package com.rivera.learningmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rivera.learningmanagement.entity.LearningLog;
import com.rivera.learningmanagement.service.LearningLogService;

@RestController
@RequestMapping("/api/learning-logs")
@Validated
public class LearningLogController {

    private final LearningLogService learningLogService;

    public LearningLogController(LearningLogService learningLogService) {
        this.learningLogService = learningLogService;
    }

    @PostMapping
    public ResponseEntity<LearningLog> createLearningLog(@Valid @RequestBody LearningLog learningLog) {
        learningLog = learningLogService.createLearningLog(learningLog);
        return new ResponseEntity<>(learningLog, HttpStatus.CREATED);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<LearningLog> updateLearningLog(@PathVariable Long logId, @Valid @RequestBody LearningLog learningLog) {
        learningLog = learningLogService.updateLearningLog(logId, learningLog);
        return ResponseEntity.ok(learningLog);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLearningLog(@PathVariable Long logId) {
        learningLogService.deleteLearningLog(logId);
        return ResponseEntity.noContent().build();
    }
}
