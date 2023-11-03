package com.rivera.learningmanagement.repository;

import com.rivera.learningmanagement.entity.Registration;
import com.rivera.learningmanagement.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {

    @Query("SELECT COUNT(r.id) FROM Registration r WHERE r.student = :student")
    int countSelectedCoursesByStudent(@Param("student") Student student);

}
