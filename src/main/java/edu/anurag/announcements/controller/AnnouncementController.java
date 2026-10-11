package edu.anurag.announcements.controller;

import edu.anurag.announcements.dto.AnnouncementDto;
import edu.anurag.announcements.model.*;
import edu.anurag.announcements.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final AcknowledgementService acknowledgementService;
    private final NotificationService notificationService;

    public AnnouncementController(AnnouncementService announcementService,
                                  UserService userService,
                                  DepartmentService departmentService,
                                  AcknowledgementService acknowledgementService,
                                  NotificationService notificationService) {
        this.announcementService = announcementService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.acknowledgementService = acknowledgementService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String listAnnouncements(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam(value = "category", required = false) AnnouncementCategory category,
                                    @RequestParam(value = "priority", required = false) Priority priority,
                                    @RequestParam(value = "query", required = false) String query,
                                    Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);

        List<Announcement> announcements = announcementService.searchVisibleAnnouncements(currentUser, category, priority, query);
        model.addAttribute("announcements", announcements);
        model.addAttribute("categories", AnnouncementCategory.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("searchQuery", query);
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "announcements/list";
    }

    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("announcementDto", new AnnouncementDto());
        model.addAttribute("departments", departmentService.getAllActiveDepartments());
        model.addAttribute("categories", AnnouncementCategory.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("roles", Role.values());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));
        return "announcements/create";
    }

    @PostMapping("/create")
    public String submitCreate(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @ModelAttribute("announcementDto") AnnouncementDto dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("departments", departmentService.getAllActiveDepartments());
            model.addAttribute("categories", AnnouncementCategory.values());
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("roles", Role.values());
            model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));
            return "announcements/create";
        }

        Announcement created = announcementService.createAnnouncement(dto, currentUser);
        redirectAttributes.addFlashAttribute("successMessage",
                "Announcement \"" + created.getTitle() + "\" published successfully! Automated in-app and email notifications have been dispatched.");
        return "redirect:/announcements/" + created.getId();
    }

    @GetMapping("/{id}")
    public String viewAnnouncement(@PathVariable("id") Long id,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);

        Announcement announcement = announcementService.getAndIncrementViews(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        boolean hasAcknowledged = acknowledgementService.hasUserAcknowledged(id, currentUser.getId());
        Acknowledgement userAck = acknowledgementService.getAcknowledgement(id, currentUser.getId()).orElse(null);

        model.addAttribute("announcement", announcement);
        model.addAttribute("hasAcknowledged", hasAcknowledged);
        model.addAttribute("userAck", userAck);
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));
        model.addAttribute("isAuthorOrAdmin", announcement.getAuthor().getId().equals(currentUser.getId()) || currentUser.getRole() == Role.ROLE_ADMIN);

        return "announcements/view";
    }

    @PostMapping("/{id}/acknowledge")
    public String acknowledgeAnnouncement(@PathVariable("id") Long id,
                                          @RequestParam(value = "remarks", required = false) String remarks,
                                          @AuthenticationPrincipal UserDetails userDetails,
                                          RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        Announcement announcement = announcementService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        acknowledgementService.recordAcknowledgement(announcement, currentUser, remarks);
        redirectAttributes.addFlashAttribute("successMessage", "Acknowledgement recorded! Thank you for confirming you have noted this announcement.");

        return "redirect:/announcements/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        Announcement announcement = announcementService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));

        if (!announcement.getAuthor().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ROLE_ADMIN) {
            return "redirect:/access-denied";
        }

        AnnouncementDto dto = AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .category(announcement.getCategory())
                .priority(announcement.getPriority())
                .isPublic(announcement.isPublic())
                .expiryDate(announcement.getExpiresAt() != null ? announcement.getExpiresAt().toLocalDate() : null)
                .build();

        model.addAttribute("announcementDto", dto);
        model.addAttribute("announcement", announcement);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("categories", AnnouncementCategory.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));

        return "announcements/edit";
    }

    @PostMapping("/{id}/edit")
    public String submitEdit(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             @Valid @ModelAttribute("announcementDto") AnnouncementDto dto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("categories", AnnouncementCategory.values());
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("unreadCount", notificationService.getUnreadCount(currentUser.getId()));
            return "announcements/edit";
        }

        announcementService.updateAnnouncement(id, dto, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement updated successfully!");
        return "redirect:/announcements/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteAnnouncement(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        announcementService.deleteAnnouncement(id, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement has been removed.");
        return "redirect:/announcements";
    }
}
