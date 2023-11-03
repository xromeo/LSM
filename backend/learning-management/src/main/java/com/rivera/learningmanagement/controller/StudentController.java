package com.rivera.learningmanagement.controller;

import com.rivera.learningmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.rivera.learningmanagement.entity.*;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody Student student) {
        Student registeredStudent = studentService.registerStudent(student);
        return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
