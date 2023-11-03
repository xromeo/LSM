package com.rivera.learningmanagement.service;

import com.rivera.learningmanagement.entity.Course;
import org.springframework.stereotype.Service;
import com.rivera.learningmanagement.entity.LearningLog;
import com.rivera.learningmanagement.repository.LearningLogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LearningLogService {

    private final LearningLogRepository learningLogRepository;

    public LearningLogService(LearningLogRepository learningLogRepository) {
        this.learningLogRepository = learningLogRepository;
    }

    public LearningLog createLearningLog(LearningLog learningLog) {
        return learningLogRepository.save(learningLog);
    }
    public LearningLog updateLearningLog(Long logId, LearningLog updatedLog) {
        Optional<LearningLog> existingLogOptional = learningLogRepository.findById(logId);

        if (existingLogOptional.isPresent()) {
            LearningLog existingLog = existingLogOptional.get();

            existingLog.setTaskCategory(updatedLog.getTaskCategory());
            existingLog.setTaskDescription(updatedLog.getTaskDescription());
            existingLog.setTimeSpent(updatedLog.getTimeSpent());

            return learningLogRepository.save(existingLog);
        }

        return null;
    }

    public List<LearningLog> getAllLearningLogs() {
        return StreamSupport.stream(learningLogRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public LearningLog getLearningLogById(Long id) {
        Optional<LearningLog> learningLogOptional = learningLogRepository.findById(id);
        return learningLogOptional.orElse(null);
    }

    public void deleteLearningLog(Long logId) {
        learningLogRepository.deleteById(logId);
    }
}
