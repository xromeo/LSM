package com.rivera.learningmanagement.controller;

import com.rivera.learningmanagement.entity.Course;
import com.rivera.learningmanagement.service.CourseService;
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
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Test
    public void testCreateCourse() throws Exception {
         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"name\": \"Course Name\",\n" +
                                "  \"startDate\": \"2023-11-03\",\n" +
                                "  \"endDate\": \"2023-12-31\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andReturn();

        List<Course> courses = courseService.getAllCourses();
        assertEquals(1, courses.size());
    }

    @Test
    public void testGetAllCourses() throws Exception {
        Course course1 = new Course("Course 1", LocalDate.now(), LocalDate.now().plusDays(30));
        Course course2 = new Course("Course 2", LocalDate.now(), LocalDate.now().plusDays(45));

        courseService.createCourse(course1);
        courseService.createCourse(course2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Course> courses = courseService.getAllCourses();
        String content = result.getResponse().getContentAsString();
        assertEquals(courses.size(), content.split("name").length - 1);
    }

    @Test
    public void testGetCourseById() throws Exception {
        Course course = new Course("Course 0", LocalDate.now(), LocalDate.now().plusDays(45));

        Course createdCourse = courseService.createCourse(course);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/" + createdCourse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":" + createdCourse.getId()));
        assertTrue(content.contains("\"name\":\"Course 0\""));
    }
}
