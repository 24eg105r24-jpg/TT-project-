package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.AnnouncementTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementTargetRepository extends JpaRepository<AnnouncementTarget, Long> {
    List<AnnouncementTarget> findByAnnouncementId(Long announcementId);
}
