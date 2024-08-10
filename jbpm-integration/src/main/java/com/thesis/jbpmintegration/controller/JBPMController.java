package com.thesis.jbpmintegration.controller;



import com.thesis.jbpmintegration.domain.ProcessStartRequest;
import com.thesis.jbpmintegration.domain.VariableChangePayload;
import com.thesis.jbpmintegration.service.JBPMService;
import com.thesis.jbpmintegration.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.thesis.jbpmintegration.service.DiagramService;

@RestController
@RequestMapping("/api/jbpm")
public class JBPMController {
    @Value("jbpmReturnChannelNew")
    private String returnChannel;

    @Autowired
    private RabbitService rabbitService;
    @PostMapping("/notifyProcessStart")
    public void handleProcessStart(@RequestBody String processStart) {
        System.out.println(processStart);
        rabbitService.sendMessage(returnChannel, processStart);
    }
    @PostMapping("/notifyNodeChange")
    public void handleNodeChange(@RequestBody String nodeChange) {
        System.out.println(nodeChange);
        rabbitService.sendMessage(returnChannel, nodeChange);
    }


}

