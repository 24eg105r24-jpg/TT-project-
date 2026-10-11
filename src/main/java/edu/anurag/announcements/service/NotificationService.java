package edu.anurag.announcements.service;

import edu.anurag.announcements.model.*;
import edu.anurag.announcements.repository.NotificationRepository;
import edu.anurag.announcements.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Notification> dispatchNotificationsForAnnouncement(Announcement announcement) {
        Set<User> recipients = resolveRecipients(announcement);
        List<Notification> createdNotifications = new ArrayList<>();

        for (User recipient : recipients) {
            if (announcement.getAuthor() != null && announcement.getAuthor().getId().equals(recipient.getId())) {
                continue;
            }

            Notification notification = Notification.builder()
                    .user(recipient)
                    .announcement(announcement)
                    .title("📢 [" + announcement.getPriority().getLabel().toUpperCase() + "] " + announcement.getTitle())
                    .message(summarizeContent(announcement.getContent()))
                    .isRead(false)
                    .channel("IN_APP")
                    .createdAt(LocalDateTime.now())
                    .build();

            createdNotifications.add(notification);

            log.info("📧 [EMAIL DISPATCHED] To: {} ({}) | Subject: Announcement: {} | Channel: EMAIL",
                    recipient.getEmail(), recipient.getFullName(), announcement.getTitle());
        }

        return notificationRepository.saveAll(createdNotifications);
    }

    public Set<User> resolveRecipients(Announcement announcement) {
        Set<User> recipients = new HashSet<>();

        if (announcement.getTargets() == null || announcement.getTargets().isEmpty()) {
            recipients.addAll(userRepository.findByActiveTrue());
            return recipients;
        }

        for (AnnouncementTarget target : announcement.getTargets()) {
            if (target.getTargetDepartment() == null && target.getTargetYear() == null
                    && target.getTargetSection() == null && target.getTargetRole() == null) {
                recipients.addAll(userRepository.findByActiveTrue());
            } else {
                List<User> matched = userRepository.findMatchingRecipients(
                        target.getTargetRole(),
                        target.getTargetDepartment(),
                        target.getTargetYear(),
                        target.getTargetSection()
                );
                recipients.addAll(matched);
            }
        }

        return recipients;
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getRecentNotificationsForUser(Long userId) {
        return notificationRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            if (n.getUser().getId().equals(userId) && !n.isRead()) {
                n.setRead(true);
                n.setReadAt(LocalDateTime.now());
                notificationRepository.save(n);
            }
        });
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadForUser(userId, LocalDateTime.now());
    }

    private String summarizeContent(String text) {
        if (text == null) return "";
        String clean = text.replaceAll("<[^>]*>", "").replaceAll("[#*`_\\[\\]]", " ").trim();
        if (clean.length() <= 140) return clean;
        return clean.substring(0, 137) + "...";
    }
}
