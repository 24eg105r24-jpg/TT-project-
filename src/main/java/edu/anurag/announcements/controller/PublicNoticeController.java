package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.Announcement;
import edu.anurag.announcements.service.AnnouncementService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PublicNoticeController {

    private final AnnouncementService announcementService;

    public PublicNoticeController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/public/announcements")
    public String publicAnnouncements(Model model) {
        List<Announcement> notices = announcementService.getPublicAnnouncements();
        model.addAttribute("notices", notices);
        model.addAttribute("pageTitle", "Official Public Notices & Circulars | Anurag University");
        model.addAttribute("metaDescription", "Official campus-wide public notices, placement circulars, exam notifications and event announcements from Anurag University.");
        return "public/list";
    }

    @GetMapping("/public/announcements/{id}")
    public String publicAnnouncementDetail(@PathVariable("id") Long id, Model model) {
        Announcement announcement = announcementService.getById(id)
                .filter(Announcement::isPublic)
                .orElseThrow(() -> new IllegalArgumentException("Public notice not found: " + id));

        model.addAttribute("announcement", announcement);
        model.addAttribute("pageTitle", announcement.getTitle() + " | Anurag University Public Circular");
        model.addAttribute("metaDescription", announcement.getTitle() + " - Official circular released by "
                + announcement.getAuthor().getFullName() + " for Anurag University.");
        return "public/view";
    }

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String generateSitemap() {
        List<Announcement> notices = announcementService.getPublicAnnouncements();
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        xml.append("  <url>\n");
        xml.append("    <loc>https://announcements.anurag.edu.in/</loc>\n");
        xml.append("    <changefreq>daily</changefreq>\n");
        xml.append("    <priority>1.0</priority>\n");
        xml.append("  </url>\n");

        xml.append("  <url>\n");
        xml.append("    <loc>https://announcements.anurag.edu.in/public/announcements</loc>\n");
        xml.append("    <changefreq>hourly</changefreq>\n");
        xml.append("    <priority>0.9</priority>\n");
        xml.append("  </url>\n");

        xml.append("  <url>\n");
        xml.append("    <loc>https://announcements.anurag.edu.in/about</loc>\n");
        xml.append("    <changefreq>monthly</changefreq>\n");
        xml.append("    <priority>0.7</priority>\n");
        xml.append("  </url>\n");

        for (Announcement a : notices) {
            xml.append("  <url>\n");
            xml.append("    <loc>https://announcements.anurag.edu.in/public/announcements/").append(a.getId()).append("</loc>\n");
            xml.append("    <lastmod>").append(a.getPublishedAt().toLocalDate()).append("</lastmod>\n");
            xml.append("    <changefreq>weekly</changefreq>\n");
            xml.append("    <priority>0.8</priority>\n");
            xml.append("  </url>\n");
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    @GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String generateRobotsTxt() {
        return """
                User-agent: *
                Allow: /
                Allow: /public/
                Allow: /about
                Allow: /sitemap.xml
                Disallow: /dashboard
                Disallow: /admin/
                Disallow: /profile
                Disallow: /notifications
                
                Sitemap: https://announcements.anurag.edu.in/sitemap.xml
                """;
    }
}
