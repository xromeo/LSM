package com.rivera.learningmanagement.controller;

import com.rivera.learningmanagement.entity.Registration;
import com.rivera.learningmanagement.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping()
    public ResponseEntity<Registration> addRegistration(@RequestBody Registration registration) {
        Registration addedRegistration = registrationService.addRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRegistration);

    }
}
