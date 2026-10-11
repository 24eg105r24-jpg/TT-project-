package edu.anurag.announcements.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Column(nullable = false, length = 255)
    private String originalFileName;

    @Column(nullable = false, length = 255)
    private String storedFileName;

    @Column(length = 100)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, length = 500)
    private String filePath;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    public Attachment() {}

    public Attachment(Long id, Announcement announcement, String originalFileName, String storedFileName,
                      String fileType, Long fileSize, String filePath, LocalDateTime uploadedAt) {
        this.id = id;
        this.announcement = announcement;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }

    public static AttachmentBuilder builder() {
        return new AttachmentBuilder();
    }

    public static class AttachmentBuilder {
        private Long id;
        private Announcement announcement;
        private String originalFileName;
        private String storedFileName;
        private String fileType;
        private Long fileSize;
        private String filePath;
        private LocalDateTime uploadedAt;

        public AttachmentBuilder id(Long id) { this.id = id; return this; }
        public AnnouncementBuilder announcement(Announcement announcement) { this.announcement = announcement; return null; }
        public AttachmentBuilder originalFileName(String originalFileName) { this.originalFileName = originalFileName; return this; }
        public AttachmentBuilder storedFileName(String storedFileName) { this.storedFileName = storedFileName; return this; }
        public AttachmentBuilder fileType(String fileType) { this.fileType = fileType; return this; }
        public AttachmentBuilder fileSize(Long fileSize) { this.fileSize = fileSize; return this; }
        public AttachmentBuilder filePath(String filePath) { this.filePath = filePath; return this; }
        public AttachmentBuilder uploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public Attachment build() {
            return new Attachment(id, announcement, originalFileName, storedFileName, fileType, fileSize, filePath, uploadedAt);
        }
    }

    public void setAnnouncement(Announcement announcement) { this.announcement = announcement; }

    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }

    public String getFormattedSize() {
        if (fileSize == null || fileSize <= 0) return "0 B";
        if (fileSize < 1024) return fileSize + " B";
        if (fileSize < 1024 * 1024) return String.format("%.1f KB", fileSize / 1024.0);
        return String.format("%.1f MB", fileSize / (1024.0 * 1024.0));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Announcement getAnnouncement() { return announcement; }

    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }

    public String getStoredFileName() { return storedFileName; }
    public void setStoredFileName(String storedFileName) { this.storedFileName = storedFileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
