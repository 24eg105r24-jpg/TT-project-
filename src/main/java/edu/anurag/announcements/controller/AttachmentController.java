package edu.anurag.announcements.controller;

import edu.anurag.announcements.model.Attachment;
import edu.anurag.announcements.service.AttachmentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/attachments/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable("id") Long id) {
        Attachment attachment = attachmentService.getAttachmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attachment not found: " + id));

        Resource resource = attachmentService.loadFileAsResource(attachment);
        String contentType = attachment.getFileType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getOriginalFileName() + "\"")
                .body(resource);
    }
}
