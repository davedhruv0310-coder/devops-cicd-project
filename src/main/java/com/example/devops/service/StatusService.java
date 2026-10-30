package com.example.devops.service;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public String getApplicationName() {
        return "DevOps Demo Application";
    }

    public String getStatus() {
        return "UP";
    }

    public String getWelcomeMessage() {
        return "Welcome to our automated CI/CD project";
    }
}
