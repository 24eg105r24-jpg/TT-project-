package edu.anurag.announcements.dto;

import java.time.LocalDateTime;

public class RecipientStatusDto {
    private Long userId;
    private String fullName;
    private String email;
    private String rollNumber;
    private String roleName;
    private String departmentName;
    private Integer academicYear;
    private String section;
    private boolean read;
    private LocalDateTime readAt;
    private boolean acknowledged;
    private LocalDateTime acknowledgedAt;
    private String remarks;

    public RecipientStatusDto() {}

    public RecipientStatusDto(Long userId, String fullName, String email, String rollNumber, String roleName,
                              String departmentName, Integer academicYear, String section, boolean read,
                              LocalDateTime readAt, boolean acknowledged, LocalDateTime acknowledgedAt, String remarks) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.rollNumber = rollNumber;
        this.roleName = roleName;
        this.departmentName = departmentName;
        this.academicYear = academicYear;
        this.section = section;
        this.read = read;
        this.readAt = readAt;
        this.acknowledged = acknowledged;
        this.acknowledgedAt = acknowledgedAt;
        this.remarks = remarks;
    }

    public static RecipientStatusDtoBuilder builder() {
        return new RecipientStatusDtoBuilder();
    }

    public static class RecipientStatusDtoBuilder {
        private Long userId;
        private String fullName;
        private String email;
        private String rollNumber;
        private String roleName;
        private String departmentName;
        private Integer academicYear;
        private String section;
        private boolean read;
        private LocalDateTime readAt;
        private boolean acknowledged;
        private LocalDateTime acknowledgedAt;
        private String remarks;

        public RecipientStatusDtoBuilder userId(Long userId) { this.userId = userId; return this; }
        public RecipientStatusDtoBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public RecipientStatusDtoBuilder email(String email) { this.email = email; return this; }
        public RecipientStatusDtoBuilder rollNumber(String rollNumber) { this.rollNumber = rollNumber; return this; }
        public RecipientStatusDtoBuilder roleName(String roleName) { this.roleName = roleName; return this; }
        public RecipientStatusDtoBuilder departmentName(String departmentName) { this.departmentName = departmentName; return this; }
        public RecipientStatusDtoBuilder academicYear(Integer academicYear) { this.academicYear = academicYear; return this; }
        public RecipientStatusDtoBuilder section(String section) { this.section = section; return this; }
        public RecipientStatusDtoBuilder read(boolean read) { this.read = read; return this; }
        public RecipientStatusDtoBuilder readAt(LocalDateTime readAt) { this.readAt = readAt; return this; }
        public RecipientStatusDtoBuilder acknowledged(boolean acknowledged) { this.acknowledged = acknowledged; return this; }
        public RecipientStatusDtoBuilder acknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; return this; }
        public RecipientStatusDtoBuilder remarks(String remarks) { this.remarks = remarks; return this; }

        public RecipientStatusDto build() {
            return new RecipientStatusDto(userId, fullName, email, rollNumber, roleName, departmentName, academicYear, section, read, readAt, acknowledged, acknowledgedAt, remarks);
        }
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }

    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }

    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
