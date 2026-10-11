package edu.anurag.announcements.service;

import edu.anurag.announcements.model.Announcement;
import edu.anurag.announcements.model.Attachment;
import edu.anurag.announcements.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    private final AttachmentRepository attachmentRepository;

    @Value("${app.upload.dir:uploads/announcements}")
    private String uploadDir;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Transactional
    public List<Attachment> storeFiles(List<MultipartFile> files, Announcement announcement) {
        List<Attachment> savedAttachments = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return savedAttachments;
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            log.error("Could not create upload directory: {}", uploadPath, e);
            throw new RuntimeException("Could not initialize upload folder", e);
        }

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String extension = "";
            int extIndex = originalFileName.lastIndexOf(".");
            if (extIndex > 0) {
                extension = originalFileName.substring(extIndex);
            }

            String storedFileName = UUID.randomUUID().toString() + extension;
            Path targetLocation = uploadPath.resolve(storedFileName);

            try {
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                Attachment attachment = Attachment.builder()
                        .originalFileName(originalFileName)
                        .storedFileName(storedFileName)
                        .fileType(file.getContentType())
                        .fileSize(file.getSize())
                        .filePath(targetLocation.toString())
                        .uploadedAt(LocalDateTime.now())
                        .build();
                attachment.setAnnouncement(announcement);

                savedAttachments.add(attachmentRepository.save(attachment));
            } catch (IOException e) {
                log.error("Failed to store file: {}", originalFileName, e);
            }
        }

        return savedAttachments;
    }

    public Optional<Attachment> getAttachmentById(Long id) {
        return attachmentRepository.findById(id);
    }

    public Resource loadFileAsResource(Attachment attachment) {
        try {
            Path filePath = Paths.get(attachment.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + attachment.getOriginalFileName());
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed file URL: " + attachment.getOriginalFileName(), ex);
        }
    }
}
