package edu.anurag.announcements.dto;

import edu.anurag.announcements.model.AnnouncementCategory;
import edu.anurag.announcements.model.Priority;
import edu.anurag.announcements.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class AnnouncementDto {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Content body is required")
    private String content;

    @NotNull(message = "Category is required")
    private AnnouncementCategory category;

    @NotNull(message = "Priority is required")
    private Priority priority = Priority.MEDIUM;

    private boolean isPublic = false;

    private LocalDate expiryDate;

    private String targetScope;
    private Long targetDepartmentId;
    private Integer targetYear;
    private String targetSection;
    private Role targetRole;

    private List<MultipartFile> files;

    public AnnouncementDto() {}

    public AnnouncementDto(Long id, String title, String content, AnnouncementCategory category, Priority priority,
                           boolean isPublic, LocalDate expiryDate, String targetScope, Long targetDepartmentId,
                           Integer targetYear, String targetSection, Role targetRole, List<MultipartFile> files) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.isPublic = isPublic;
        this.expiryDate = expiryDate;
        this.targetScope = targetScope;
        this.targetDepartmentId = targetDepartmentId;
        this.targetYear = targetYear;
        this.targetSection = targetSection;
        this.targetRole = targetRole;
        this.files = files;
    }

    public static AnnouncementDtoBuilder builder() {
        return new AnnouncementDtoBuilder();
    }

    public static class AnnouncementDtoBuilder {
        private Long id;
        private String title;
        private String content;
        private AnnouncementCategory category;
        private Priority priority = Priority.MEDIUM;
        private boolean isPublic = false;
        private LocalDate expiryDate;
        private String targetScope;
        private Long targetDepartmentId;
        private Integer targetYear;
        private String targetSection;
        private Role targetRole;
        private List<MultipartFile> files;

        public AnnouncementDtoBuilder id(Long id) { this.id = id; return this; }
        public AnnouncementDtoBuilder title(String title) { this.title = title; return this; }
        public AnnouncementDtoBuilder content(String content) { this.content = content; return this; }
        public AnnouncementDtoBuilder category(AnnouncementCategory category) { this.category = category; return this; }
        public AnnouncementDtoBuilder priority(Priority priority) { this.priority = priority; return this; }
        public AnnouncementDtoBuilder isPublic(boolean isPublic) { this.isPublic = isPublic; return this; }
        public AnnouncementDtoBuilder expiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; return this; }
        public AnnouncementDtoBuilder targetScope(String targetScope) { this.targetScope = targetScope; return this; }
        public AnnouncementDtoBuilder targetDepartmentId(Long targetDepartmentId) { this.targetDepartmentId = targetDepartmentId; return this; }
        public AnnouncementDtoBuilder targetYear(Integer targetYear) { this.targetYear = targetYear; return this; }
        public AnnouncementDtoBuilder targetSection(String targetSection) { this.targetSection = targetSection; return this; }
        public AnnouncementDtoBuilder targetRole(Role targetRole) { this.targetRole = targetRole; return this; }
        public AnnouncementDtoBuilder files(List<MultipartFile> files) { this.files = files; return this; }

        public AnnouncementDto build() {
            return new AnnouncementDto(id, title, content, category, priority, isPublic, expiryDate, targetScope, targetDepartmentId, targetYear, targetSection, targetRole, files);
        }
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

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getTargetScope() { return targetScope; }
    public void setTargetScope(String targetScope) { this.targetScope = targetScope; }

    public Long getTargetDepartmentId() { return targetDepartmentId; }
    public void setTargetDepartmentId(Long targetDepartmentId) { this.targetDepartmentId = targetDepartmentId; }

    public Integer getTargetYear() { return targetYear; }
    public void setTargetYear(Integer targetYear) { this.targetYear = targetYear; }

    public String getTargetSection() { return targetSection; }
    public void setTargetSection(String targetSection) { this.targetSection = targetSection; }

    public Role getTargetRole() { return targetRole; }
    public void setTargetRole(Role targetRole) { this.targetRole = targetRole; }

    public List<MultipartFile> getFiles() { return files; }
    public void setFiles(List<MultipartFile> files) { this.files = files; }
}
