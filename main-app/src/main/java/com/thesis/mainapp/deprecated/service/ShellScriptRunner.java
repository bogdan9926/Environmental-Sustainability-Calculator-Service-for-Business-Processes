package com.thesis.mainapp.deprecated.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@Service
public class ShellScriptRunner {

    private void runCommand(List<String> commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            StringBuilder errorOutput = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }
            throw new RuntimeException("Error executing command: " + errorOutput);
        }
    }

    public void runShellScript() throws IOException, InterruptedException {
        // Change directory and install
        runCommand(List.of("bash", "-c", "cd /home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar && mvn install"));
        System.out.println("1");
        // Get the container ID
        String getContainerIdCommand = "docker ps --filter \"ancestor=jboss/jbpm-server-full:latest\" --format \"{{.ID}}\"";
        ProcessBuilder containerIdBuilder = new ProcessBuilder();
        containerIdBuilder.command("bash", "-c", getContainerIdCommand);
        System.out.println("2");

        Process containerIdProcess = containerIdBuilder.start();
        containerIdProcess.waitFor();
        String containerId = new String(containerIdProcess.getInputStream().readAllBytes()).trim();

        // Copy files to container
        if (!containerId.isEmpty()) {
            runCommand(List.of("bash", "-c", "docker cp /home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar " + containerId + ":/home/jboss/application"));
            runCommand(List.of("bash", "-c", "docker cp /home/bogdan/.m2/repository/com/company/mainapp-kjar " + containerId + ":/opt/jboss/.m2/repository/com/company/mainapp-kjar"));
        } else {
            throw new RuntimeException("Docker container not found.");
        }
        System.out.println("3");

    }
}