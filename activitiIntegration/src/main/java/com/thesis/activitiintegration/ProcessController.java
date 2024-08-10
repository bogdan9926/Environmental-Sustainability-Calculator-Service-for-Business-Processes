package com.thesis.activitiintegration;

package com.thesis.activitiintegration;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/processes")
public class ProcessController {

    private final ActivitiRuntimeService activitiRuntimeService;

    @Autowired
    public ProcessController(ActivitiRuntimeService activitiRuntimeService) {
        this.activitiRuntimeService = activitiRuntimeService;
    }

    @GetMapping("/start-vacation-request")
    public String startVacationRequestProcess() {
        Task task = activitiRuntimeService.startAndCompleteProcess();
        if (task != null) {
            return "Current task: " + task.getName();
        } else {
            return "Process completed or no task found.";
        }
    }
}
