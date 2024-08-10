package com.thesis.mainapp;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiRuntimeService {

    @Autowired
    private RuntimeService runtimeService;

    public void startMyProcess() {
        runtimeService.startProcessInstanceByKey("Process_1y80xt6");
    }

}
