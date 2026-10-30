package com.example.devops;

import com.example.devops.service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatusServiceTest {

    private StatusService statusService;

    @BeforeEach
    void setUp() {
        statusService = new StatusService();
    }

    @Test
    @DisplayName("Verify application name is returned correctly")
    void testGetApplicationName() {
        String appName = statusService.getApplicationName();
        assertNotNull(appName);
        assertEquals("DevOps Demo Application", appName);
    }

    @Test
    @DisplayName("Verify application status is UP")
    void testGetStatus() {
        String status = statusService.getStatus();
        assertNotNull(status);
        assertEquals("UP", status);
    }

    @Test
    @DisplayName("Verify welcome message is returned correctly")
    void testGetWelcomeMessage() {
        String message = statusService.getWelcomeMessage();
        assertNotNull(message);
        assertEquals("Welcome to our automated CI/CD project", message);
    }
}
