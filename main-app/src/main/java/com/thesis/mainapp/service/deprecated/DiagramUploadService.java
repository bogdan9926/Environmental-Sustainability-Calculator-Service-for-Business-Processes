package com.thesis.mainapp.service.deprecated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Random;

public class DiagramUploadService {
//    public String uploadDiagram(DiagramUploadRequest diagramUploadRequest) throws IOException, InterruptedException {
//        MultipartFile diagram = diagramUploadRequest.getDiagram();
//        String message = transferFile(diagram);
//        String channelName = "";
//        String returnString = "";
//        switch (diagramUploadRequest.getPlatform()) {
//            case ("camunda"): {
//                message = addCamundaConfiguration(diagramUploadRequest, message);
//                channelName = "camundaChannel";
//                break;
//            }
//            case ("jbpm"): {
//                Random random = new Random();
//                Integer randomInt = random.nextInt();
//                containerId = randomInt.toString();
//                containerName = randomInt.toString();
//                message = addJbpmConfiguration(diagramUploadRequest, message);
//
//                saveDiagramToKjar(diagram);
//                channelName = "jbpmChannel";
//                returnString = containerId;
//                break;
//            }
////            case ("activiti"): {
////                message = addActivitiConfiguration(diagramUploadRequest, message);
////                channelName = "activitiChannel";
////                break;
////            }
//        }
//
//        rabbitService.sendMessage(channelName, message);
//
//        return returnString;
//    }
//
//    public String addCamundaConfiguration(DiagramUploadRequest diagramUploadRequest, String destinationPath) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        DeployDiagramJson json = new DeployDiagramJson();
//
//        json.setFileLocation(destinationPath);
//        json.setProcessId(diagramUploadRequest.getProcessId());
//        return objectMapper.writeValueAsString(json);
//
//    }
//
//    public String addJbpmConfiguration(DiagramUploadRequest diagramUploadRequest, String destinationPath) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JbpmJson json = new JbpmJson();
//
//        json.setFileLocation(destinationPath);
//        json.setProcessId(diagramUploadRequest.getProcessId());
//        json.setArtifactId(artifactId);
//        json.setContainerId(containerId);
//        json.setContainerName(containerName);
//        json.setGroupId(groupId);
//        json.setVersion(version);
//
//        return objectMapper.writeValueAsString(json);
//    }
//
//    public String transferFile(MultipartFile diagram) throws IOException {
//        Path filePath = Paths.get(location, diagram.getOriginalFilename());
//        File file = filePath.toFile();
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(diagram.getBytes());
//        }
//        return filePath.toString();
//    }
//
//    public void saveDiagramToKjar(MultipartFile diagram) {
//        String kjarPath = "/home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar/src/main/resources/";
//        String fileName = diagram.getOriginalFilename();
//
//        try {
//            Path path = Paths.get(kjarPath + fileName);
//            Files.copy(diagram.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("File uploaded successfully: " + fileName);
//        } catch (Exception e) {
//            System.out.println("File upload failed: " + fileName);
//        }
//    }

//    public String addActivitiConfiguration(DiagramUploadRequest diagramUploadRequest, String destinationPath) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ActivitiJson json = new ActivitiJson();
//        json.setFileLocation(destinationPath);
//        json.setProcessName(diagramUploadRequest.getProcessName());
//        return objectMapper.writeValueAsString(json);
//    }

//    public void startProcess(ProcessStartRequest processStartRequest) throws IOException, InterruptedException {
//        String channelName = "";
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        StartProcessJson startProcessJson = new StartProcessJson();
//        startProcessJson.setProcessId(processStartRequest.getProcessId());
//        switch (processStartRequest.getPlatform()) {
//            case ("camunda"): {
//                channelName = "camundaChannel";
//                break;
//            }
//            case ("jbpm"): {
//                startProcessJson.setContainerId(processStartRequest.getContainerId());
//                channelName = "jbpmChannel";
//                break;
//            }
//        }
//
//        rabbitService.sendMessage(channelName, objectMapper.writeValueAsString(startProcessJson));
//
//    }
}
