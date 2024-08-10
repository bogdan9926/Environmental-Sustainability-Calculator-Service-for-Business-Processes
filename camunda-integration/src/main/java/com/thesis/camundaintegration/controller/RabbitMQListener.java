//package com.thesis.camundaintegration.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.thesis.camundaintegration.domain.CamundaJson;
//import com.thesis.camundaintegration.domain.DeployDiagramJson;
//import com.thesis.camundaintegration.domain.StartProcessJson;
//import com.thesis.camundaintegration.service.ZeebeeService;
//import org.checkerframework.checker.units.qual.A;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//public class RabbitMQListener {
//    @Autowired
//    private ZeebeeService zeebeeService;
//
//    @RabbitListener(queues = "camundaChannel")
//    public void receiveMessage(String message) throws JsonProcessingException {
//        System.out.println("Received message from queue: " + message);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            CamundaJson messageJson = objectMapper.readValue(message, DeployDiagramJson.class);
//            if (messageJson instanceof DeployDiagramJson) {
//                try {
//
//                    byte[] diagramBytes = zeebeeService.getDiagram(((DeployDiagramJson) messageJson).getFileLocation());
//                    zeebeeService.deployDiagram(diagramBytes,((DeployDiagramJson) messageJson).getprocessId());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            try {
//                CamundaJson messageJson = objectMapper.readValue(message, StartProcessJson.class);
//                if (messageJson instanceof StartProcessJson) {
//                    zeebeeService.startProcess(((StartProcessJson) messageJson).getprocessId());
//                }
//            } catch (Exception ex) {
//                System.err.println("Failed to deserialize message: " + new String(message));
//                ex.printStackTrace();
//            }
//        }
//    }
//}
//
