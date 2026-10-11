package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    long countByUserIdAndIsReadFalse(Long userId);

    long countByAnnouncementId(Long announcementId);

    long countByAnnouncementIdAndIsReadTrue(Long announcementId);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :now WHERE n.user.id = :userId AND n.isRead = false")
    void markAllAsReadForUser(@Param("userId") Long userId, @Param("now") LocalDateTime now);

    boolean existsByUserIdAndAnnouncementId(Long userId, Long announcementId);
}
