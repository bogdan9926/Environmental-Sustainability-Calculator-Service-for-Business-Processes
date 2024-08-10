package com.thesis.mainapp.deprecated.controller;

import com.thesis.mainapp.deprecated.domain.payloads.DiagramUploadRequest;
import com.thesis.mainapp.deprecated.domain.payloads.ProcessStartRequest;
import com.thesis.mainapp.deprecated.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AppController {
    @Autowired
    private AppService appService;
    @PostMapping("/upload-diagram")
    public ResponseEntity<String> uploadDiagram(@RequestParam("file") MultipartFile file, @RequestParam("processId") String processId,
                                                @RequestParam("platform") String platform) throws IOException, InterruptedException {
        try {
            DiagramUploadRequest diagramUploadRequest = new DiagramUploadRequest();
            diagramUploadRequest.setDiagram(file);
            diagramUploadRequest.setPlatform(platform);
            diagramUploadRequest.setProcessId(processId);
            String containerName = "";
            if (platform.equals("jbpm")) {
                containerName = appService.uploadDiagram(diagramUploadRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(containerName);
            }
            appService.uploadDiagram(diagramUploadRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagram upload request finished!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload diagram: " + e.getMessage());
        }

    }

    @PostMapping("/start-process")
    public ResponseEntity<String> startProcess(@RequestParam("processId") String processId,
                                               @RequestParam(value = "containerName", required = false, defaultValue = "null") String containerName,
                                               @RequestParam("platform") String platform) throws IOException, InterruptedException {
        try {
            ProcessStartRequest processStartRequest = new ProcessStartRequest();
            processStartRequest.setProcessId(processId);
            processStartRequest.setContainerId(containerName);
            processStartRequest.setPlatform(platform);
            appService.startProcess(processStartRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Process start request finished!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start process: " + e.getMessage());
        }

    }
}
