package com.rivera.learningmanagement.controller;

import com.rivera.learningmanagement.entity.Student;
import com.rivera.learningmanagement.service.StudentService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    public void testRegisterStudent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"firstName\": \"John\",\n" +
                                "  \"lastName\": \"Doe\",\n" +
                                "  \"email\": \"johndoe@example.com\",\n" +
                                "  \"dateOfBirth\": \"1990-01-01\",\n" +
                                "  \"address\": \"123 Main St\",\n" +
                                "  \"phoneNumber\": \"+1234567890\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andReturn();

        List<Student> students = studentService.getAllStudents();
        assertEquals(1, students.size());
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Smith");
        student1.setEmail("alice@example.com");
        student1.setDateOfBirth(LocalDate.of(1995, 5, 15));
        student1.setPhoneNumber("123-456-7890");
        student1.setAddress("123 Main St.");

        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("Johnson");
        student2.setEmail("bob@example.com");
        student2.setDateOfBirth(LocalDate.of(1990, 8, 22));
        student2.setPhoneNumber("987-654-3210");
        student2.setAddress("456 Elm St.");


        studentService.registerStudent(student1);
        studentService.registerStudent(student2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Student> students = studentService.getAllStudents();
        String content = result.getResponse().getContentAsString();
        assertEquals(students.size(), content.split("firstName").length - 1);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setFirstName("Eve");
        student.setLastName("Adams");
        student.setDateOfBirth(LocalDate.of(1997,7,7));
        student.setEmail("algo@email.com");
        student.setAddress("La Paz");
        student.setPhoneNumber("123-474147-582");

        Student registeredStudent = studentService.registerStudent(student);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/students/" + registeredStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":" + registeredStudent.getId()));
        assertTrue(content.contains("\"firstName\":\"Eve\""));
    }
}

