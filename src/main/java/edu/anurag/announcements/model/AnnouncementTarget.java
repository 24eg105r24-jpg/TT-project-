package edu.anurag.announcements.model;

import jakarta.persistence.*;

@Entity
@Table(name = "announcement_targets")
public class AnnouncementTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_department_id")
    private Department targetDepartment;

    @Column(name = "target_year")
    private Integer targetYear;

    @Column(name = "target_section", length = 10)
    private String targetSection;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_role", length = 20)
    private Role targetRole;

    @Column(name = "target_scope_description", length = 200)
    private String targetScopeDescription;

    public AnnouncementTarget() {}

    public AnnouncementTarget(Long id, Announcement announcement, Department targetDepartment,
                              Integer targetYear, String targetSection, Role targetRole, String targetScopeDescription) {
        this.id = id;
        this.announcement = announcement;
        this.targetDepartment = targetDepartment;
        this.targetYear = targetYear;
        this.targetSection = targetSection;
        this.targetRole = targetRole;
        this.targetScopeDescription = targetScopeDescription;
    }

    public static AnnouncementTargetBuilder builder() {
        return new AnnouncementTargetBuilder();
    }

    public static class AnnouncementTargetBuilder {
        private Long id;
        private Announcement announcement;
        private Department targetDepartment;
        private Integer targetYear;
        private String targetSection;
        private Role targetRole;
        private String targetScopeDescription;

        public AnnouncementTargetBuilder id(Long id) { this.id = id; return this; }
        public AnnouncementTargetBuilder announcement(Announcement announcement) { this.announcement = announcement; return this; }
        public AnnouncementTargetBuilder targetDepartment(Department targetDepartment) { this.targetDepartment = targetDepartment; return this; }
        public AnnouncementTargetBuilder targetYear(Integer targetYear) { this.targetYear = targetYear; return this; }
        public AnnouncementTargetBuilder targetSection(String targetSection) { this.targetSection = targetSection; return this; }
        public AnnouncementTargetBuilder targetRole(Role targetRole) { this.targetRole = targetRole; return this; }
        public AnnouncementTargetBuilder targetScopeDescription(String targetScopeDescription) { this.targetScopeDescription = targetScopeDescription; return this; }

        public AnnouncementTarget build() {
            return new AnnouncementTarget(id, announcement, targetDepartment, targetYear, targetSection, targetRole, targetScopeDescription);
        }
    }

    public String computeReadableScope() {
        StringBuilder sb = new StringBuilder();
        if (targetDepartment == null && targetYear == null && targetSection == null && targetRole == null) {
            return "Campus-wide (All Departments & Roles)";
        }
        if (targetRole != null) {
            sb.append(targetRole.getDisplayName()).append("s only; ");
        }
        if (targetDepartment != null) {
            sb.append(targetDepartment.getCode()).append(" Dept; ");
        } else {
            sb.append("All Departments; ");
        }
        if (targetYear != null) {
            sb.append("Year ").append(targetYear).append("; ");
        }
        if (targetSection != null && !targetSection.isBlank()) {
            sb.append("Section ").append(targetSection);
        }
        return sb.toString().replaceAll("; $", "");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Announcement getAnnouncement() { return announcement; }
    public void setAnnouncement(Announcement announcement) { this.announcement = announcement; }

    public Department getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(Department targetDepartment) { this.targetDepartment = targetDepartment; }

    public Integer getTargetYear() { return targetYear; }
    public void setTargetYear(Integer targetYear) { this.targetYear = targetYear; }

    public String getTargetSection() { return targetSection; }
    public void setTargetSection(String targetSection) { this.targetSection = targetSection; }

    public Role getTargetRole() { return targetRole; }
    public void setTargetRole(Role targetRole) { this.targetRole = targetRole; }

    public String getTargetScopeDescription() { return targetScopeDescription; }
    public void setTargetScopeDescription(String targetScopeDescription) { this.targetScopeDescription = targetScopeDescription; }
}
