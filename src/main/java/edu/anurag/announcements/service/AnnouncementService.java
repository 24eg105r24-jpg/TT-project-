package edu.anurag.announcements.service;

import edu.anurag.announcements.dto.AnnouncementDto;
import edu.anurag.announcements.model.*;
import edu.anurag.announcements.repository.AnnouncementRepository;
import edu.anurag.announcements.repository.AnnouncementTargetRepository;
import edu.anurag.announcements.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementService.class);

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTargetRepository targetRepository;
    private final DepartmentRepository departmentRepository;
    private final AttachmentService attachmentService;
    private final NotificationService notificationService;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               AnnouncementTargetRepository targetRepository,
                               DepartmentRepository departmentRepository,
                               AttachmentService attachmentService,
                               NotificationService notificationService) {
        this.announcementRepository = announcementRepository;
        this.targetRepository = targetRepository;
        this.departmentRepository = departmentRepository;
        this.attachmentService = attachmentService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Announcement createAnnouncement(AnnouncementDto dto, User author) {
        Announcement announcement = Announcement.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .author(author)
                .isPublic(dto.isPublic())
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now())
                .expiresAt(dto.getExpiryDate() != null ? dto.getExpiryDate().atTime(23, 59, 59) : null)
                .build();

        AnnouncementTarget target = new AnnouncementTarget();
        target.setAnnouncement(announcement);

        if ("ALL_COLLEGE".equalsIgnoreCase(dto.getTargetScope()) || dto.getTargetScope() == null) {
            target.setTargetScopeDescription("Campus-wide (All Departments, Years, Sections & Roles)");
        } else {
            if (dto.getTargetDepartmentId() != null) {
                Department dept = departmentRepository.findById(dto.getTargetDepartmentId()).orElse(null);
                target.setTargetDepartment(dept);
            }
            target.setTargetYear(dto.getTargetYear());
            target.setTargetSection(dto.getTargetSection() != null && !dto.getTargetSection().isBlank()
                    ? dto.getTargetSection().trim().toUpperCase() : null);
            target.setTargetRole(dto.getTargetRole());
            target.setTargetScopeDescription(target.computeReadableScope());
        }

        announcement.addTarget(target);

        Announcement saved = announcementRepository.save(announcement);

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            attachmentService.storeFiles(dto.getFiles(), saved);
        }

        notificationService.dispatchNotificationsForAnnouncement(saved);

        return saved;
    }

    @Transactional
    public Announcement updateAnnouncement(Long id, AnnouncementDto dto, User user) {
        Announcement existing = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        if (!existing.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new SecurityException("Unauthorized to edit this announcement");
        }

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setCategory(dto.getCategory());
        existing.setPriority(dto.getPriority());
        existing.setPublic(dto.isPublic());
        if (dto.getExpiryDate() != null) {
            existing.setExpiresAt(dto.getExpiryDate().atTime(23, 59, 59));
        }

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            attachmentService.storeFiles(dto.getFiles(), existing);
        }

        return announcementRepository.save(existing);
    }

    public Optional<Announcement> getById(Long id) {
        return announcementRepository.findById(id);
    }

    @Transactional
    public Optional<Announcement> getAndIncrementViews(Long id) {
        return announcementRepository.findById(id).map(a -> {
            a.setViewCount(a.getViewCount() + 1);
            return announcementRepository.save(a);
        });
    }

    public List<Announcement> getVisibleAnnouncements(User user) {
        return announcementRepository.findVisibleForUser(
                AnnouncementStatus.PUBLISHED,
                user.getId(),
                user.getRole(),
                user.getDepartment(),
                user.getAcademicYear(),
                user.getSection()
        );
    }

    public List<Announcement> searchVisibleAnnouncements(User user, AnnouncementCategory category, Priority priority, String query) {
        return announcementRepository.searchVisibleForUser(
                AnnouncementStatus.PUBLISHED,
                user.getId(),
                user.getRole(),
                user.getDepartment(),
                user.getAcademicYear(),
                user.getSection(),
                category,
                priority,
                query
        );
    }

    public List<Announcement> getPublicAnnouncements() {
        return announcementRepository.findByIsPublicTrueAndStatusOrderByPublishedAtDesc(AnnouncementStatus.PUBLISHED);
    }

    public List<Announcement> getAnnouncementsByAuthor(User author) {
        return announcementRepository.findByAuthorOrderByPublishedAtDesc(author);
    }

    public List<Announcement> getAllPublished() {
        return announcementRepository.findByStatusOrderByPublishedAtDesc(AnnouncementStatus.PUBLISHED);
    }

    @Transactional
    public void deleteAnnouncement(Long id, User user) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        if (!announcement.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new SecurityException("Unauthorized to delete this announcement");
        }

        announcementRepository.delete(announcement);
    }

    @Transactional
    public void archiveAnnouncement(Long id, User user) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        if (!announcement.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new SecurityException("Unauthorized to archive this announcement");
        }

        announcement.setStatus(AnnouncementStatus.ARCHIVED);
        announcementRepository.save(announcement);
    }

    public long countPublished() {
        return announcementRepository.countByStatus(AnnouncementStatus.PUBLISHED);
    }
}
