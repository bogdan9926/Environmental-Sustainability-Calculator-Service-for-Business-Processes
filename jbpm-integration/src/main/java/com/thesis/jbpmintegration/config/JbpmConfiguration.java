package com.thesis.jbpmintegration.config;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JbpmConfiguration {
    private static final String KIE_SERVER_PASSWORD = "kieserver1!";
    private static final String USER_PASSWORD = "wbadmin";
    private static final String KIE_SERVER_ROLE = "kie-server";
    private static final String GUEST_ROLE = "guest";
    private static final String ADMIN_ROLE = "Administrators";
    private static final String ENGINEERING_ROLE = "engineering";
    private static final String HR_ROLE = "HR";
    private static final String IT_ROLE = "IT";
    private static final String ACCOUNTING_ROLE = "HR";
    @Value("${jbpm.url}")
    private String url;
    @Bean
    public KieServicesClient kieServicesClient() {
//        String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
        String serverUrl = url;
        String username = "wbadmin";
        String password = "wbadmin";
        KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration(serverUrl, username, password);
        // Configure timeout, marshalling, etc., as needed

        return KieServicesFactory.newKieServicesClient(configuration);
    }
}

