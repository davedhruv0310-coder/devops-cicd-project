package com.example.devops.controller;

import com.example.devops.service.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    private final StatusService statusService;

    public HomeController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("application", statusService.getApplicationName());
        response.put("message", statusService.getWelcomeMessage());
        return response;
    }

    @GetMapping("/api/status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("application", statusService.getApplicationName());
        response.put("status", statusService.getStatus());
        return response;
    }
}
