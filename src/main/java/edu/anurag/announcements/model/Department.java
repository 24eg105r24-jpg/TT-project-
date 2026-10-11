package edu.anurag.announcements.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code; // e.g. "CSE", "IT", "ECE"

    @Column(nullable = false, length = 150)
    private String name; // e.g. "Computer Science & Engineering"

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    public Department() {}

    public Department(Long id, String code, String name, String description, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public static DepartmentBuilder builder() {
        return new DepartmentBuilder();
    }

    public static class DepartmentBuilder {
        private Long id;
        private String code;
        private String name;
        private String description;
        private boolean active = true;

        public DepartmentBuilder id(Long id) { this.id = id; return this; }
        public DepartmentBuilder code(String code) { this.code = code; return this; }
        public DepartmentBuilder name(String name) { this.name = name; return this; }
        public DepartmentBuilder description(String description) { this.description = description; return this; }
        public DepartmentBuilder active(boolean active) { this.active = active; return this; }

        public Department build() {
            return new Department(id, code, name, description, active);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
