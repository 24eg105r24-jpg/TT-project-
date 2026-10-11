package edu.anurag.announcements.model;

public enum AnnouncementCategory {
    ACADEMIC("Academic", "🎓", "badge-academic"),
    EXAMINATION("Examination", "📝", "badge-exam"),
    PLACEMENT("Placement & Training", "💼", "badge-placement"),
    EVENT("Events & Fests", "🎉", "badge-event"),
    ADMINISTRATIVE("Administrative", "🏛️", "badge-admin"),
    URGENT_ALERT("Urgent Campus Alert", "🚨", "badge-urgent"),
    SPORTS("Sports & Culture", "🏆", "badge-sports"),
    HOLIDAY("Holidays & Circulars", "📅", "badge-holiday"),
    HOSTEL("Hostel & Campus Life", "🏢", "badge-hostel");

    private final String label;
    private final String icon;
    private final String cssClass;

    AnnouncementCategory(String label, String icon, String cssClass) {
        this.label = label;
        this.icon = icon;
        this.cssClass = cssClass;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public String getCssClass() {
        return cssClass;
    }
}
