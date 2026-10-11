package edu.anurag.announcements.dto;

import java.util.Map;

public class AnalyticsDto {
    private long totalAnnouncements;
    private long publishedAnnouncements;
    private long publicAnnouncements;
    private long totalUsers;
    private long totalStudents;
    private long totalFaculty;
    private long totalNotificationsSent;
    private long totalNotificationsRead;
    private double overallReadRate;
    private long totalAcknowledgements;
    private double overallAckRate;
    private Map<String, Long> categoryCounts;
    private Map<String, Long> priorityCounts;
    private Map<String, Long> departmentUserCounts;

    public AnalyticsDto() {}

    public AnalyticsDto(long totalAnnouncements, long publishedAnnouncements, long publicAnnouncements,
                        long totalUsers, long totalStudents, long totalFaculty,
                        long totalNotificationsSent, long totalNotificationsRead, double overallReadRate,
                        long totalAcknowledgements, double overallAckRate,
                        Map<String, Long> categoryCounts, Map<String, Long> priorityCounts, Map<String, Long> departmentUserCounts) {
        this.totalAnnouncements = totalAnnouncements;
        this.publishedAnnouncements = publishedAnnouncements;
        this.publicAnnouncements = publicAnnouncements;
        this.totalUsers = totalUsers;
        this.totalStudents = totalStudents;
        this.totalFaculty = totalFaculty;
        this.totalNotificationsSent = totalNotificationsSent;
        this.totalNotificationsRead = totalNotificationsRead;
        this.overallReadRate = overallReadRate;
        this.totalAcknowledgements = totalAcknowledgements;
        this.overallAckRate = overallAckRate;
        this.categoryCounts = categoryCounts;
        this.priorityCounts = priorityCounts;
        this.departmentUserCounts = departmentUserCounts;
    }

    public static AnalyticsDtoBuilder builder() {
        return new AnalyticsDtoBuilder();
    }

    public static class AnalyticsDtoBuilder {
        private long totalAnnouncements;
        private long publishedAnnouncements;
        private long publicAnnouncements;
        private long totalUsers;
        private long totalStudents;
        private long totalFaculty;
        private long totalNotificationsSent;
        private long totalNotificationsRead;
        private double overallReadRate;
        private long totalAcknowledgements;
        private double overallAckRate;
        private Map<String, Long> categoryCounts;
        private Map<String, Long> priorityCounts;
        private Map<String, Long> departmentUserCounts;

        public AnalyticsDtoBuilder totalAnnouncements(long totalAnnouncements) { this.totalAnnouncements = totalAnnouncements; return this; }
        public AnalyticsDtoBuilder publishedAnnouncements(long publishedAnnouncements) { this.publishedAnnouncements = publishedAnnouncements; return this; }
        public AnalyticsDtoBuilder publicAnnouncements(long publicAnnouncements) { this.publicAnnouncements = publicAnnouncements; return this; }
        public AnalyticsDtoBuilder totalUsers(long totalUsers) { this.totalUsers = totalUsers; return this; }
        public AnalyticsDtoBuilder totalStudents(long totalStudents) { this.totalStudents = totalStudents; return this; }
        public AnalyticsDtoBuilder totalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; return this; }
        public AnalyticsDtoBuilder totalNotificationsSent(long totalNotificationsSent) { this.totalNotificationsSent = totalNotificationsSent; return this; }
        public AnalyticsDtoBuilder totalNotificationsRead(long totalNotificationsRead) { this.totalNotificationsRead = totalNotificationsRead; return this; }
        public AnalyticsDtoBuilder overallReadRate(double overallReadRate) { this.overallReadRate = overallReadRate; return this; }
        public AnalyticsDtoBuilder totalAcknowledgements(long totalAcknowledgements) { this.totalAcknowledgements = totalAcknowledgements; return this; }
        public AnalyticsDtoBuilder overallAckRate(double overallAckRate) { this.overallAckRate = overallAckRate; return this; }
        public AnalyticsDtoBuilder categoryCounts(Map<String, Long> categoryCounts) { this.categoryCounts = categoryCounts; return this; }
        public AnalyticsDtoBuilder priorityCounts(Map<String, Long> priorityCounts) { this.priorityCounts = priorityCounts; return this; }
        public AnalyticsDtoBuilder departmentUserCounts(Map<String, Long> departmentUserCounts) { this.departmentUserCounts = departmentUserCounts; return this; }

        public AnalyticsDto build() {
            return new AnalyticsDto(totalAnnouncements, publishedAnnouncements, publicAnnouncements, totalUsers, totalStudents, totalFaculty, totalNotificationsSent, totalNotificationsRead, overallReadRate, totalAcknowledgements, overallAckRate, categoryCounts, priorityCounts, departmentUserCounts);
        }
    }

    public long getTotalAnnouncements() { return totalAnnouncements; }
    public void setTotalAnnouncements(long totalAnnouncements) { this.totalAnnouncements = totalAnnouncements; }

    public long getPublishedAnnouncements() { return publishedAnnouncements; }
    public void setPublishedAnnouncements(long publishedAnnouncements) { this.publishedAnnouncements = publishedAnnouncements; }

    public long getPublicAnnouncements() { return publicAnnouncements; }
    public void setPublicAnnouncements(long publicAnnouncements) { this.publicAnnouncements = publicAnnouncements; }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; }

    public long getTotalNotificationsSent() { return totalNotificationsSent; }
    public void setTotalNotificationsSent(long totalNotificationsSent) { this.totalNotificationsSent = totalNotificationsSent; }

    public long getTotalNotificationsRead() { return totalNotificationsRead; }
    public void setTotalNotificationsRead(long totalNotificationsRead) { this.totalNotificationsRead = totalNotificationsRead; }

    public double getOverallReadRate() { return overallReadRate; }
    public void setOverallReadRate(double overallReadRate) { this.overallReadRate = overallReadRate; }

    public long getTotalAcknowledgements() { return totalAcknowledgements; }
    public void setTotalAcknowledgements(long totalAcknowledgements) { this.totalAcknowledgements = totalAcknowledgements; }

    public double getOverallAckRate() { return overallAckRate; }
    public void setOverallAckRate(double overallAckRate) { this.overallAckRate = overallAckRate; }

    public Map<String, Long> getCategoryCounts() { return categoryCounts; }
    public void setCategoryCounts(Map<String, Long> categoryCounts) { this.categoryCounts = categoryCounts; }

    public Map<String, Long> getPriorityCounts() { return priorityCounts; }
    public void setPriorityCounts(Map<String, Long> priorityCounts) { this.priorityCounts = priorityCounts; }

    public Map<String, Long> getDepartmentUserCounts() { return departmentUserCounts; }
    public void setDepartmentUserCounts(Map<String, Long> departmentUserCounts) { this.departmentUserCounts = departmentUserCounts; }
}
