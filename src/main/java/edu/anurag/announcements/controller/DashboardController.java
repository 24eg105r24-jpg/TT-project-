package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.*;
import edu.anurag.announcements.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final AnnouncementService announcementService;
    private final NotificationService notificationService;
    private final AcknowledgementService acknowledgementService;

    public DashboardController(UserService userService,
                               AnnouncementService announcementService,
                               NotificationService notificationService,
                               AcknowledgementService acknowledgementService) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.notificationService = notificationService;
        this.acknowledgementService = acknowledgementService;
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);

        List<Announcement> announcements = announcementService.getVisibleAnnouncements(currentUser);
        model.addAttribute("announcements", announcements);

        List<Announcement> urgentAnnouncements = announcements.stream()
                .filter(a -> a.getPriority() == Priority.URGENT)
                .limit(3)
                .toList();
        model.addAttribute("urgentAnnouncements", urgentAnnouncements);

        long unreadCount = notificationService.getUnreadCount(currentUser.getId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("recentNotifications", notificationService.getRecentNotificationsForUser(currentUser.getId()));

        long totalNotices = announcements.size();
        long pendingAckCount = announcements.stream()
                .filter(a -> !acknowledgementService.hasUserAcknowledged(a.getId(), currentUser.getId()))
                .count();

        model.addAttribute("totalNotices", totalNotices);
        model.addAttribute("pendingAckCount", pendingAckCount);
        model.addAttribute("categories", AnnouncementCategory.values());
        model.addAttribute("priorities", Priority.values());

        if (currentUser.getRole() == Role.ROLE_FACULTY || currentUser.getRole() == Role.ROLE_ADMIN) {
            List<Announcement> authored = announcementService.getAnnouncementsByAuthor(currentUser);
            model.addAttribute("authoredAnnouncements", authored);
        }

        return "dashboard/index";
    }
}
