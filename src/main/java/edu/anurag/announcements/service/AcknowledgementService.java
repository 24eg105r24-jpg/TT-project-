package edu.anurag.announcements.service;

import edu.anurag.announcements.model.Acknowledgement;
import edu.anurag.announcements.model.Announcement;
import edu.anurag.announcements.model.User;
import edu.anurag.announcements.repository.AcknowledgementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcknowledgementService {

    private final AcknowledgementRepository acknowledgementRepository;

    public AcknowledgementService(AcknowledgementRepository acknowledgementRepository) {
        this.acknowledgementRepository = acknowledgementRepository;
    }

    public boolean hasUserAcknowledged(Long announcementId, Long userId) {
        return acknowledgementRepository.existsByAnnouncementIdAndUserId(announcementId, userId);
    }

    public Optional<Acknowledgement> getAcknowledgement(Long announcementId, Long userId) {
        return acknowledgementRepository.findByAnnouncementIdAndUserId(announcementId, userId);
    }

    @Transactional
    public Acknowledgement recordAcknowledgement(Announcement announcement, User user, String remarks) {
        if (hasUserAcknowledged(announcement.getId(), user.getId())) {
            return getAcknowledgement(announcement.getId(), user.getId()).orElse(null);
        }

        Acknowledgement ack = Acknowledgement.builder()
                .announcement(announcement)
                .user(user)
                .acknowledgedAt(LocalDateTime.now())
                .remarks(remarks != null && !remarks.isBlank() ? remarks.trim() : "Noted & Acknowledged")
                .build();

        return acknowledgementRepository.save(ack);
    }

    public long countByAnnouncementId(Long announcementId) {
        return acknowledgementRepository.countByAnnouncementId(announcementId);
    }

    public List<Acknowledgement> getAcknowledgementsForAnnouncement(Long announcementId) {
        return acknowledgementRepository.findByAnnouncementIdOrderByAcknowledgedAtDesc(announcementId);
    }
}
