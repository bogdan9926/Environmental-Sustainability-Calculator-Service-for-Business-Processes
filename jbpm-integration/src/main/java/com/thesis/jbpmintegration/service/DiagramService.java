package com.thesis.jbpmintegration.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DiagramService {
    public String uploadDiagram(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String kjarPath = "/home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar/src/main/resources/META-INF";
        try {
            Path path = Paths.get(kjarPath + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return "File uploaded successfully: " + fileName;
        } catch (Exception e) {
            return "File upload failed: " + fileName;
        }
    }
}
