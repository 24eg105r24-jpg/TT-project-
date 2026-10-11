package edu.anurag.announcements.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acknowledgements", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "announcement_id"})
})
public class Acknowledgement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Column(name = "acknowledged_at", nullable = false)
    private LocalDateTime acknowledgedAt;

    @Column(length = 255)
    private String remarks;

    public Acknowledgement() {}

    public Acknowledgement(Long id, User user, Announcement announcement, LocalDateTime acknowledgedAt, String remarks) {
        this.id = id;
        this.user = user;
        this.announcement = announcement;
        this.acknowledgedAt = acknowledgedAt;
        this.remarks = remarks;
    }

    public static AcknowledgementBuilder builder() {
        return new AcknowledgementBuilder();
    }

    public static class AcknowledgementBuilder {
        private Long id;
        private User user;
        private Announcement announcement;
        private LocalDateTime acknowledgedAt;
        private String remarks;

        public AcknowledgementBuilder id(Long id) { this.id = id; return this; }
        public AcknowledgementBuilder user(User user) { this.user = user; return this; }
        public AcknowledgementBuilder announcement(Announcement announcement) { this.announcement = announcement; return this; }
        public AcknowledgementBuilder acknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; return this; }
        public AcknowledgementBuilder remarks(String remarks) { this.remarks = remarks; return this; }

        public Acknowledgement build() {
            return new Acknowledgement(id, user, announcement, acknowledgedAt, remarks);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (acknowledgedAt == null) {
            acknowledgedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Announcement getAnnouncement() { return announcement; }
    public void setAnnouncement(Announcement announcement) { this.announcement = announcement; }

    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
