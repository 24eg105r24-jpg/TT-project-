package edu.anurag.announcements.model;

public enum AnnouncementStatus {
    PUBLISHED("Published"),
    DRAFT("Draft"),
    ARCHIVED("Archived");

    private final String label;

    AnnouncementStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
