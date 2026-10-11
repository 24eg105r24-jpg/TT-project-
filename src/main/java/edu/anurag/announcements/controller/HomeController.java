package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.Announcement;
import edu.anurag.announcements.service.AnnouncementService;
import edu.anurag.announcements.service.DepartmentService;
import edu.anurag.announcements.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AnnouncementService announcementService;
    private final UserService userService;
    private final DepartmentService departmentService;

    public HomeController(AnnouncementService announcementService,
                          UserService userService,
                          DepartmentService departmentService) {
        this.announcementService = announcementService;
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }

        List<Announcement> publicAnnouncements = announcementService.getPublicAnnouncements();
        model.addAttribute("publicAnnouncements", publicAnnouncements.stream().limit(6).toList());
        model.addAttribute("totalAnnouncements", announcementService.countPublished());
        model.addAttribute("totalUsers", userService.countTotalUsers());
        model.addAttribute("totalDepartments", departmentService.getAllActiveDepartments().size());

        return "home/index";
    }

    @GetMapping("/about")
    public String aboutProject(Model model) {
        model.addAttribute("pageTitle", "Project Abstract & System Documentation");
        return "home/about";
    }
}
