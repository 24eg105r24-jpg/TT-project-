package edu.anurag.announcements.dto;

import edu.anurag.announcements.model.Announcement;
import java.util.List;
import java.util.Map;

public class ReachReportDto {
    private Announcement announcement;
    private long totalTargeted;
    private long totalDelivered;
    private long readCount;
    private double readRatePercentage;
    private long acknowledgedCount;
    private double ackRatePercentage;
    private Map<String, Long> departmentBreakdown;
    private List<RecipientStatusDto> recipientStatuses;

    public ReachReportDto() {}

    public ReachReportDto(Announcement announcement, long totalTargeted, long totalDelivered,
                          long readCount, double readRatePercentage, long acknowledgedCount,
                          double ackRatePercentage, Map<String, Long> departmentBreakdown,
                          List<RecipientStatusDto> recipientStatuses) {
        this.announcement = announcement;
        this.totalTargeted = totalTargeted;
        this.totalDelivered = totalDelivered;
        this.readCount = readCount;
        this.readRatePercentage = readRatePercentage;
        this.acknowledgedCount = acknowledgedCount;
        this.ackRatePercentage = ackRatePercentage;
        this.departmentBreakdown = departmentBreakdown;
        this.recipientStatuses = recipientStatuses;
    }

    public static ReachReportDtoBuilder builder() {
        return new ReachReportDtoBuilder();
    }

    public static class ReachReportDtoBuilder {
        private Announcement announcement;
        private long totalTargeted;
        private long totalDelivered;
        private long readCount;
        private double readRatePercentage;
        private long acknowledgedCount;
        private double ackRatePercentage;
        private Map<String, Long> departmentBreakdown;
        private List<RecipientStatusDto> recipientStatuses;

        public ReachReportDtoBuilder announcement(Announcement announcement) { this.announcement = announcement; return this; }
        public ReachReportDtoBuilder totalTargeted(long totalTargeted) { this.totalTargeted = totalTargeted; return this; }
        public ReachReportDtoBuilder totalDelivered(long totalDelivered) { this.totalDelivered = totalDelivered; return this; }
        public ReachReportDtoBuilder readCount(long readCount) { this.readCount = readCount; return this; }
        public ReachReportDtoBuilder readRatePercentage(double readRatePercentage) { this.readRatePercentage = readRatePercentage; return this; }
        public ReachReportDtoBuilder acknowledgedCount(long acknowledgedCount) { this.acknowledgedCount = acknowledgedCount; return this; }
        public ReachReportDtoBuilder ackRatePercentage(double ackRatePercentage) { this.ackRatePercentage = ackRatePercentage; return this; }
        public ReachReportDtoBuilder departmentBreakdown(Map<String, Long> departmentBreakdown) { this.departmentBreakdown = departmentBreakdown; return this; }
        public ReachReportDtoBuilder recipientStatuses(List<RecipientStatusDto> recipientStatuses) { this.recipientStatuses = recipientStatuses; return this; }

        public ReachReportDto build() {
            return new ReachReportDto(announcement, totalTargeted, totalDelivered, readCount, readRatePercentage, acknowledgedCount, ackRatePercentage, departmentBreakdown, recipientStatuses);
        }
    }

    public Announcement getAnnouncement() { return announcement; }
    public void setAnnouncement(Announcement announcement) { this.announcement = announcement; }

    public long getTotalTargeted() { return totalTargeted; }
    public void setTotalTargeted(long totalTargeted) { this.totalTargeted = totalTargeted; }

    public long getTotalDelivered() { return totalDelivered; }
    public void setTotalDelivered(long totalDelivered) { this.totalDelivered = totalDelivered; }

    public long getReadCount() { return readCount; }
    public void setReadCount(long readCount) { this.readCount = readCount; }

    public double getReadRatePercentage() { return readRatePercentage; }
    public void setReadRatePercentage(double readRatePercentage) { this.readRatePercentage = readRatePercentage; }

    public long getAcknowledgedCount() { return acknowledgedCount; }
    public void setAcknowledgedCount(long acknowledgedCount) { this.acknowledgedCount = acknowledgedCount; }

    public double getAckRatePercentage() { return ackRatePercentage; }
    public void setAckRatePercentage(double ackRatePercentage) { this.ackRatePercentage = ackRatePercentage; }

    public Map<String, Long> getDepartmentBreakdown() { return departmentBreakdown; }
    public void setDepartmentBreakdown(Map<String, Long> departmentBreakdown) { this.departmentBreakdown = departmentBreakdown; }

    public List<RecipientStatusDto> getRecipientStatuses() { return recipientStatuses; }
    public void setRecipientStatuses(List<RecipientStatusDto> recipientStatuses) { this.recipientStatuses = recipientStatuses; }
}
