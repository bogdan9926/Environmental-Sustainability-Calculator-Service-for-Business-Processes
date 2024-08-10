package com.thesis.camundaintegration;

//import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CamundaIntegrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(CamundaIntegrationApplication.class, args);
	}

}
