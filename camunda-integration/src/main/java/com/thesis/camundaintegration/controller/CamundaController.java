package com.thesis.camundaintegration.controller;


import com.thesis.camundaintegration.service.RabbitService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/camunda")

public class CamundaController {
    @Value("camundaReturnChannelNew")
    private String returnChannel;

    @Autowired
    private RabbitService rabbitService;
    @PostMapping("/startExecutionListener")
    public String startExecutionListener(@RequestBody Map<String, Object> payload) {
        System.out.println("payload received" + payload.toString());
        JSONObject jsonObject = new JSONObject(payload);
        rabbitService.sendMessage(returnChannel, jsonObject.toString());
        return "payload received";


    }
    @PostMapping("/endExecutionListener")
    public String endExecutionListener(@RequestBody Map<String, Object> payload) {
        System.out.println("payload received" + payload.toString()) ;
        JSONObject jsonObject = new JSONObject(payload);
        rabbitService.sendMessage(returnChannel, jsonObject.toString());
        return "payload received";
    }
}
