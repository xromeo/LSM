package com.rivera.learningmanagement.service;

import com.rivera.learningmanagement.exception.BusinessException;
import com.rivera.learningmanagement.exception.ErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rivera.learningmanagement.entity.Student;
import com.rivera.learningmanagement.repository.StudentRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student registerStudent(Student student) {
        if(!isAtLeast16YearsOld(student.getDateOfBirth())){
            List<ErrorModel> errorModelList = new ArrayList<>();
            errorModelList.add(new ErrorModel("LEAST_16", "Student must be at least 16 years old."));
            throw new BusinessException(errorModelList);
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    public List<Student> getAllStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public boolean isAtLeast16YearsOld(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        LocalDate dob = dateOfBirth;
        Period age = Period.between(dob, now);
        return age.getYears() >= 16;
    }
}
