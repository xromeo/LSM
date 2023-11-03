package com.rivera.learningmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rivera.learningmanagement.entity.Course;
import com.rivera.learningmanagement.entity.Registration;
import com.rivera.learningmanagement.entity.Student;
import com.rivera.learningmanagement.service.RegistrationService;
import com.rivera.learningmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddRegistration() throws Exception {

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

        Course course = new Course();
        course.setName("Course Name");
        course.setStartDate(LocalDate.of(2023, 11, 3));
        course.setEndDate(LocalDate.of(2023, 12, 31));

        MvcResult courseResult = mockMvc.perform(
                        post("/api/courses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(course))
                )
                .andExpect(status().isCreated()).andReturn();

        Course registeredCourse = objectMapper.readValue(
                courseResult.getResponse().getContentAsString(),
                Course.class
        );

        Registration registration = new Registration();
        registration.setStudent(registeredStudent);
        registration.setCourse(registeredCourse);

        MvcResult result = mockMvc.perform(
                        post("/api/registrations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registration))
                )
                .andExpect(status().isCreated())
                .andReturn();

        List<Registration> registrations = registrationService.getAllRegistrations();
        assertEquals(1, registrations.size());
    }
}
