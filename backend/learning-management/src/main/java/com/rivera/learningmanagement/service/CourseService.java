package com.rivera.learningmanagement.service;

import com.rivera.learningmanagement.entity.Course;
import com.rivera.learningmanagement.exception.BusinessException;
import com.rivera.learningmanagement.exception.ErrorModel;
import com.rivera.learningmanagement.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {


    private static final int MONTHS_TO_COMPLETE = 6;

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        if(!isValid(course, MONTHS_TO_COMPLETE)) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            errorModelList.add(new ErrorModel("MONTHS_6", "The course must be at least 6 months."));
            throw new BusinessException(errorModelList);
        }
        return courseRepository.save(course);

    }

    public List<Course> getAllCourses() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Course getCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.orElse(null);
    }

    public boolean isValid(Course course, int months) {
        if (course.getStartDate() == null || course.getEndDate() == null) {
            return false;
        }

        LocalDate sixMonthsLater = course.getStartDate().plusMonths(months);
        return course.getEndDate().isBefore(sixMonthsLater);
    }

}
