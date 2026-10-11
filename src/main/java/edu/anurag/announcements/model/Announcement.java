package edu.anurag.announcements.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AnnouncementCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority = Priority.MEDIUM;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AnnouncementStatus status = AnnouncementStatus.PUBLISHED;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "view_count")
    private int viewCount = 0;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementTarget> targets = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Acknowledgement> acknowledgements = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    public Announcement() {}

    public Announcement(Long id, String title, String content, AnnouncementCategory category, Priority priority,
                        User author, boolean isPublic, AnnouncementStatus status, LocalDateTime publishedAt,
                        LocalDateTime expiresAt, int viewCount, List<AnnouncementTarget> targets,
                        List<Attachment> attachments, List<Acknowledgement> acknowledgements, List<Notification> notifications) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.author = author;
        this.isPublic = isPublic;
        this.status = status != null ? status : AnnouncementStatus.PUBLISHED;
        this.publishedAt = publishedAt;
        this.expiresAt = expiresAt;
        this.viewCount = viewCount;
        if (targets != null) this.targets = targets;
        if (attachments != null) this.attachments = attachments;
        if (acknowledgements != null) this.acknowledgements = acknowledgements;
        if (notifications != null) this.notifications = notifications;
    }

    public static AnnouncementBuilder builder() {
        return new AnnouncementBuilder();
    }

    public static class AnnouncementBuilder {
        private Long id;
        private String title;
        private String content;
        private AnnouncementCategory category;
        private Priority priority = Priority.MEDIUM;
        private User author;
        private boolean isPublic = false;
        private AnnouncementStatus status = AnnouncementStatus.PUBLISHED;
        private LocalDateTime publishedAt;
        private LocalDateTime expiresAt;
        private int viewCount = 0;
        private List<AnnouncementTarget> targets = new ArrayList<>();
        private List<Attachment> attachments = new ArrayList<>();
        private List<Acknowledgement> acknowledgements = new ArrayList<>();
        private List<Notification> notifications = new ArrayList<>();

        public AnnouncementBuilder id(Long id) { this.id = id; return this; }
        public AnnouncementBuilder title(String title) { this.title = title; return this; }
        public AnnouncementBuilder content(String content) { this.content = content; return this; }
        public AnnouncementBuilder category(AnnouncementCategory category) { this.category = category; return this; }
        public AnnouncementBuilder priority(Priority priority) { this.priority = priority; return this; }
        public AnnouncementBuilder author(User author) { this.author = author; return this; }
        public AnnouncementBuilder isPublic(boolean isPublic) { this.isPublic = isPublic; return this; }
        public AnnouncementBuilder status(AnnouncementStatus status) { this.status = status; return this; }
        public AnnouncementBuilder publishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; return this; }
        public AnnouncementBuilder expiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; return this; }
        public AnnouncementBuilder viewCount(int viewCount) { this.viewCount = viewCount; return this; }
        public AnnouncementBuilder targets(List<AnnouncementTarget> targets) { this.targets = targets; return this; }
        public AnnouncementBuilder attachments(List<Attachment> attachments) { this.attachments = attachments; return this; }
        public AnnouncementBuilder acknowledgements(List<Acknowledgement> acknowledgements) { this.acknowledgements = acknowledgements; return this; }
        public AnnouncementBuilder notifications(List<Notification> notifications) { this.notifications = notifications; return this; }

        public Announcement build() {
            return new Announcement(id, title, content, category, priority, author, isPublic, status, publishedAt, expiresAt, viewCount, targets, attachments, acknowledgements, notifications);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    public void addTarget(AnnouncementTarget target) {
        if (targets == null) targets = new ArrayList<>();
        targets.add(target);
        target.setAnnouncement(this);
    }

    public void addAttachment(Attachment attachment) {
        if (attachments == null) attachments = new ArrayList<>();
        attachments.add(attachment);
        attachment.setAnnouncement(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public AnnouncementCategory getCategory() { return category; }
    public void setCategory(AnnouncementCategory category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }

    public AnnouncementStatus getStatus() { return status; }
    public void setStatus(AnnouncementStatus status) { this.status = status; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public List<AnnouncementTarget> getTargets() { return targets; }
    public void setTargets(List<AnnouncementTarget> targets) { this.targets = targets; }

    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    public List<Acknowledgement> getAcknowledgements() { return acknowledgements; }
    public void setAcknowledgements(List<Acknowledgement> acknowledgements) { this.acknowledgements = acknowledgements; }

    public List<Notification> getNotifications() { return notifications; }
    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }
}
