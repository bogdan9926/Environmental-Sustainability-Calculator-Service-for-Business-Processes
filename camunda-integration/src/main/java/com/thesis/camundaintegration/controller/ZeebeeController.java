//package com.thesis.camundaintegration.controller;
//
//
//import com.thesis.camundaintegration.service.RabbitService;
//import com.thesis.camundaintegration.service.ZeebeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/jbpm")
//public class ZeebeeController {
//
//    @Autowired
//    private ZeebeeService zeebeeService;
//    @Autowired
//    private RabbitService diagramService;
//
////    @PostMapping("/deploy-process")
////    public String uploadDiagram(@RequestParam("file") MultipartFile file) throws IOException {
////        try {
////            zeebeeService.deployProcess(file);
////            return "Process deployed successfully!";
////        } catch (Exception e) {
////            e.printStackTrace();
////            return "Process deployment failed!";
////        }
////    }
//
////    @PostMapping("/create-container")
////    public String createContainer(@RequestBody ContainerCreationRequest creationRequest) {
////        zeebeeService.createContainer(creationRequest);
////        return "Container creation initiated";
////    }
//
////    @PostMapping("/start-process")
////    public String startProcess(@RequestBody ProcessStartRequest processStartRequest) {
////        zeebeeService.startProcess(processStartRequest);
////        return "Container creation initiated";
////    }
//}
//
