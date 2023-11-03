package com.rivera.learningmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rivera.learningmanagement.entity.LearningLog;
import com.rivera.learningmanagement.entity.Student;
import com.rivera.learningmanagement.entity.TaskCategory;
import com.rivera.learningmanagement.service.LearningLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LearningLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LearningLogService learningLogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateLearningLog() throws Exception {

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("johndoe@example.com");
        student.setDateOfBirth(LocalDate.of(1990, 1, 1));
        student.setAddress("123 Main St");
        student.setPhoneNumber("+1234567890");

        MvcResult studentResult = mockMvc.perform(
                        post("/api/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(student))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Student registeredStudent = objectMapper.readValue(
                studentResult.getResponse().getContentAsString(),
                Student.class
        );


        LearningLog learningLog = new LearningLog();
        learningLog.setStudent(registeredStudent);
        learningLog.setLogDate(LocalDate.of(2023, 11, 3));
        learningLog.setTaskCategory(TaskCategory.RESEARCHING);
        learningLog.setTaskDescription("Research on topic X");
        learningLog.setTimeSpent(LocalDateTime.of(2023, 11, 3, 14, 30));

        MvcResult result = mockMvc.perform(
                        post("/api/learning-logs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(learningLog))
                )
                .andExpect(status().isCreated())
                .andReturn();

        List<LearningLog> logs = learningLogService.getAllLearningLogs();
        assertEquals(1, logs.size());
    }


    @Test
    public void testUpdateLearningLog() throws Exception {
        LearningLog log = new LearningLog();
        log.setLogDate(LocalDate.of(2023, 11, 3));
        log.setTaskCategory(TaskCategory.PRACTICING);
        log.setTaskDescription("Original Description");
        log.setTimeSpent(LocalDateTime.now());
        LearningLog createdLog = learningLogService.createLearningLog(log);

        MvcResult result = mockMvc.perform(
                        put("/api/learning-logs/" + createdLog.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "  \"logDate\": \"2023-11-04\"," +
                                        "  \"taskCategory\": \"RESEARCHING\"," +
                                        "  \"taskDescription\": \"Updated Description\"," +
                                        "  \"timeSpent\": \"2023-11-03T14:45:00\"" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andReturn();

        LearningLog updatedLog = learningLogService.getLearningLogById(createdLog.getId());
        assertEquals(TaskCategory.RESEARCHING, updatedLog.getTaskCategory());
        assertEquals("Updated Description", updatedLog.getTaskDescription());

    }


    @Test
    public void testDeleteLearningLog() throws Exception {
        LearningLog log = new LearningLog();
        log.setLogDate(LocalDate.of(2023,11,3));
        log.setTaskCategory(TaskCategory.PRACTICING);
        log.setTaskDescription("Original Description");
        log.setTimeSpent(LocalDateTime.now());
        LearningLog createdLog = learningLogService.createLearningLog(log);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/learning-logs/" + createdLog.getId()))
                .andExpect(status().isNoContent());

        assertTrue(learningLogService.getAllLearningLogs().isEmpty());
    }
}
