package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByStatusOrderByPublishedAtDesc(AnnouncementStatus status);

    List<Announcement> findByIsPublicTrueAndStatusOrderByPublishedAtDesc(AnnouncementStatus status);

    List<Announcement> findByAuthorOrderByPublishedAtDesc(User author);

    @Query("SELECT DISTINCT a FROM Announcement a " +
           "LEFT JOIN a.targets t " +
           "WHERE a.status = :status " +
           "AND (" +
           "   a.isPublic = true " +
           "   OR a.author.id = :userId " +
           "   OR :userRole = 'ROLE_ADMIN' " +
           "   OR (" +
           "      (t.targetRole IS NULL OR t.targetRole = :userRole) " +
           "      AND (t.targetDepartment IS NULL OR t.targetDepartment = :dept) " +
           "      AND (t.targetYear IS NULL OR t.targetYear = :year) " +
           "      AND (t.targetSection IS NULL OR t.targetSection = '' OR t.targetSection = :sec)" +
           "   )" +
           ") ORDER BY a.publishedAt DESC")
    List<Announcement> findVisibleForUser(
            @Param("status") AnnouncementStatus status,
            @Param("userId") Long userId,
            @Param("userRole") Role userRole,
            @Param("dept") Department dept,
            @Param("year") Integer year,
            @Param("sec") String sec
    );

    @Query("SELECT DISTINCT a FROM Announcement a " +
           "LEFT JOIN a.targets t " +
           "WHERE a.status = :status " +
           "AND (" +
           "   a.isPublic = true " +
           "   OR a.author.id = :userId " +
           "   OR :userRole = 'ROLE_ADMIN' " +
           "   OR (" +
           "      (t.targetRole IS NULL OR t.targetRole = :userRole) " +
           "      AND (t.targetDepartment IS NULL OR t.targetDepartment = :dept) " +
           "      AND (t.targetYear IS NULL OR t.targetYear = :year) " +
           "      AND (t.targetSection IS NULL OR t.targetSection = '' OR t.targetSection = :sec)" +
           "   )" +
           ") " +
           "AND (:category IS NULL OR a.category = :category) " +
           "AND (:priority IS NULL OR a.priority = :priority) " +
           "AND (" +
           "   :query IS NULL OR :query = '' " +
           "   OR LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "   OR a.content LIKE CONCAT('%', :query, '%')" +
           ") " +
           "ORDER BY a.publishedAt DESC")
    List<Announcement> searchVisibleForUser(
            @Param("status") AnnouncementStatus status,
            @Param("userId") Long userId,
            @Param("userRole") Role userRole,
            @Param("dept") Department dept,
            @Param("year") Integer year,
            @Param("sec") String sec,
            @Param("category") AnnouncementCategory category,
            @Param("priority") Priority priority,
            @Param("query") String query
    );

    long countByStatus(AnnouncementStatus status);
}
