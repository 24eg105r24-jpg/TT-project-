package edu.anurag.announcements.service;

import edu.anurag.announcements.dto.AnalyticsDto;
import edu.anurag.announcements.dto.ReachReportDto;
import edu.anurag.announcements.dto.RecipientStatusDto;
import edu.anurag.announcements.model.*;
import edu.anurag.announcements.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final AcknowledgementRepository acknowledgementRepository;
    private final NotificationService notificationService;

    public AnalyticsService(AnnouncementRepository announcementRepository,
                            UserRepository userRepository,
                            NotificationRepository notificationRepository,
                            AcknowledgementRepository acknowledgementRepository,
                            NotificationService notificationService) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.acknowledgementRepository = acknowledgementRepository;
        this.notificationService = notificationService;
    }

    public AnalyticsDto getSystemAnalytics() {
        long totalAnnouncements = announcementRepository.count();
        long published = announcementRepository.countByStatus(AnnouncementStatus.PUBLISHED);
        long publicNotices = announcementRepository.findByIsPublicTrueAndStatusOrderByPublishedAtDesc(AnnouncementStatus.PUBLISHED).size();

        long totalUsers = userRepository.count();
        long totalStudents = userRepository.countByRole(Role.ROLE_STUDENT);
        long totalFaculty = userRepository.countByRole(Role.ROLE_FACULTY);

        long totalNotifications = notificationRepository.count();
        long readNotifications = notificationRepository.findAll().stream().filter(Notification::isRead).count();
        double overallReadRate = totalNotifications > 0 ? (double) readNotifications / totalNotifications * 100.0 : 0.0;

        long totalAcks = acknowledgementRepository.count();
        double overallAckRate = totalNotifications > 0 ? (double) totalAcks / totalNotifications * 100.0 : 0.0;

        List<Announcement> allAnnouncements = announcementRepository.findAll();
        Map<String, Long> categoryCounts = allAnnouncements.stream()
                .collect(Collectors.groupingBy(a -> a.getCategory().getLabel(), Collectors.counting()));

        Map<String, Long> priorityCounts = allAnnouncements.stream()
                .collect(Collectors.groupingBy(a -> a.getPriority().getLabel(), Collectors.counting()));

        Map<String, Long> departmentCounts = userRepository.findAll().stream()
                .filter(u -> u.getDepartment() != null)
                .collect(Collectors.groupingBy(u -> u.getDepartment().getCode(), Collectors.counting()));

        return AnalyticsDto.builder()
                .totalAnnouncements(totalAnnouncements)
                .publishedAnnouncements(published)
                .publicAnnouncements(publicNotices)
                .totalUsers(totalUsers)
                .totalStudents(totalStudents)
                .totalFaculty(totalFaculty)
                .totalNotificationsSent(totalNotifications)
                .totalNotificationsRead(readNotifications)
                .overallReadRate(Math.round(overallReadRate * 10.0) / 10.0)
                .totalAcknowledgements(totalAcks)
                .overallAckRate(Math.round(overallAckRate * 10.0) / 10.0)
                .categoryCounts(categoryCounts)
                .priorityCounts(priorityCounts)
                .departmentUserCounts(departmentCounts)
                .build();
    }

    public ReachReportDto getAnnouncementReachReport(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + announcementId));

        Set<User> targetedUsers = notificationService.resolveRecipients(announcement);
        List<Notification> notifications = notificationRepository.findAll().stream()
                .filter(n -> n.getAnnouncement().getId().equals(announcementId))
                .toList();

        Map<Long, Notification> userNotificationMap = notifications.stream()
                .collect(Collectors.toMap(n -> n.getUser().getId(), n -> n, (existing, replacing) -> existing));

        List<Acknowledgement> acks = acknowledgementRepository.findByAnnouncementIdOrderByAcknowledgedAtDesc(announcementId);
        Map<Long, Acknowledgement> userAckMap = acks.stream()
                .collect(Collectors.toMap(a -> a.getUser().getId(), a -> a, (existing, replacing) -> existing));

        List<RecipientStatusDto> statuses = new ArrayList<>();
        long readCount = 0;
        long ackCount = 0;
        Map<String, Long> deptBreakdown = new HashMap<>();

        for (User u : targetedUsers) {
            Notification notif = userNotificationMap.get(u.getId());
            Acknowledgement ack = userAckMap.get(u.getId());

            boolean isRead = notif != null && notif.isRead();
            if (isRead) readCount++;

            boolean isAck = ack != null;
            if (isAck) ackCount++;

            String deptName = u.getDepartment() != null ? u.getDepartment().getCode() : "General";
            deptBreakdown.put(deptName, deptBreakdown.getOrDefault(deptName, 0L) + 1L);

            statuses.add(RecipientStatusDto.builder()
                    .userId(u.getId())
                    .fullName(u.getFullName())
                    .email(u.getEmail())
                    .rollNumber(u.getRollNumber())
                    .roleName(u.getRole().getDisplayName())
                    .departmentName(u.getDepartment() != null ? u.getDepartment().getName() : "All/Campus")
                    .academicYear(u.getAcademicYear())
                    .section(u.getSection())
                    .read(isRead)
                    .readAt(notif != null ? notif.getReadAt() : null)
                    .acknowledged(isAck)
                    .acknowledgedAt(ack != null ? ack.getAcknowledgedAt() : null)
                    .remarks(ack != null ? ack.getRemarks() : null)
                    .build());
        }

        long totalTargeted = targetedUsers.size();
        double readRate = totalTargeted > 0 ? (double) readCount / totalTargeted * 100.0 : 0.0;
        double ackRate = totalTargeted > 0 ? (double) ackCount / totalTargeted * 100.0 : 0.0;

        return ReachReportDto.builder()
                .announcement(announcement)
                .totalTargeted(totalTargeted)
                .totalDelivered(notifications.size())
                .readCount(readCount)
                .readRatePercentage(Math.round(readRate * 10.0) / 10.0)
                .acknowledgedCount(ackCount)
                .ackRatePercentage(Math.round(ackRate * 10.0) / 10.0)
                .departmentBreakdown(deptBreakdown)
                .recipientStatuses(statuses)
                .build();
    }
}
