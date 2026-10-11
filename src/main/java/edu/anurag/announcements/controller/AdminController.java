package edu.anurag.announcements.controller;

import edu.anurag.announcements.dto.AnalyticsDto;
import edu.anurag.announcements.dto.ReachReportDto;
import edu.anurag.announcements.model.Department;
import edu.anurag.announcements.model.User;
import edu.anurag.announcements.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AnalyticsService analyticsService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final AnnouncementService announcementService;
    private final NotificationService notificationService;

    public AdminController(AnalyticsService analyticsService,
                           UserService userService,
                           DepartmentService departmentService,
                           AnnouncementService announcementService,
                           NotificationService notificationService) {
        this.analyticsService = analyticsService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.announcementService = announcementService;
        this.notificationService = notificationService;
    }

    @GetMapping("/analytics")
    public String analyticsDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        AnalyticsDto analytics = analyticsService.getSystemAnalytics();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("analytics", analytics);
        model.addAttribute("recentAnnouncements", announcementService.getAllPublished().stream().limit(8).toList());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "admin/analytics";
    }

    @GetMapping("/announcements/{id}/reach")
    public String reachReport(@PathVariable("id") Long id,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        ReachReportDto report = analyticsService.getAnnouncementReachReport(id);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("report", report);
        model.addAttribute("announcement", report.getAnnouncement());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "admin/reach-report";
    }

    @GetMapping("/users")
    public String userList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<User> users = userService.getAllUsers();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        model.addAttribute("departments", departmentService.getAllActiveDepartments());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "admin/users";
    }

    @GetMapping("/departments")
    public String departmentsList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("departments", departments);
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "admin/departments";
    }

    @PostMapping("/departments")
    public String addDepartment(@ModelAttribute("newDepartment") Department department,
                                RedirectAttributes redirectAttributes) {
        try {
            departmentService.saveDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage", "Department added successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not add department: " + ex.getMessage());
        }
        return "redirect:/admin/departments";
    }
}
