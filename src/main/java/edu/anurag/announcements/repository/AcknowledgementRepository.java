package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.Acknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcknowledgementRepository extends JpaRepository<Acknowledgement, Long> {

    Optional<Acknowledgement> findByAnnouncementIdAndUserId(Long announcementId, Long userId);

    boolean existsByAnnouncementIdAndUserId(Long announcementId, Long userId);

    long countByAnnouncementId(Long announcementId);

    List<Acknowledgement> findByAnnouncementIdOrderByAcknowledgedAtDesc(Long announcementId);

    List<Acknowledgement> findByUserIdOrderByAcknowledgedAtDesc(Long userId);
}
