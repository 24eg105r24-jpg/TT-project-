package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.Notification;
import edu.anurag.announcements.model.User;
import edu.anurag.announcements.service.NotificationService;
import edu.anurag.announcements.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public String listNotifications(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
                                    Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);

        List<Notification> notifications = notificationService.getNotificationsForUser(currentUser.getId());
        if ("unread".equalsIgnoreCase(filter)) {
            notifications = notifications.stream().filter(n -> !n.isRead()).toList();
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("currentFilter", filter);
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "notifications/index";
    }

    @PostMapping("/{id}/read")
    public String markReadAndRedirect(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        notificationService.markAsRead(id, currentUser.getId());
        return "redirect:/notifications";
    }

    @PostMapping("/read-all")
    public String markAllRead(@AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        notificationService.markAllAsRead(currentUser.getId());
        redirectAttributes.addFlashAttribute("successMessage", "All notifications marked as read.");
        return "redirect:/notifications";
    }
}
