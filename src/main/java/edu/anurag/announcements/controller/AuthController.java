package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.Department;
import edu.anurag.announcements.model.Role;
import edu.anurag.announcements.model.User;
import edu.anurag.announcements.service.DepartmentService;
import edu.anurag.announcements.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;
    private final DepartmentService departmentService;

    public AuthController(UserService userService, DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password. Please verify your college credentials.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "You have been successfully logged out of the portal.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("departments", departmentService.getAllActiveDepartments());
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam(value = "departmentId", required = false) Long departmentId,
                               RedirectAttributes redirectAttributes) {
        try {
            if (departmentId != null) {
                Department dept = departmentService.getDepartmentById(departmentId).orElse(null);
                user.setDepartment(dept);
            }
            if (user.getRole() == null) {
                user.setRole(Role.ROLE_STUDENT);
            }
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You may now log in with your college email.");
            return "redirect:/login";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("currentUser", currentUser);
        }
        return "auth/profile";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("pageTitle", "Access Denied");
        return "error/access-denied";
    }
}
