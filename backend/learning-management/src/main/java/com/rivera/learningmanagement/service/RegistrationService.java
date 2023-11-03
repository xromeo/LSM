package com.rivera.learningmanagement.service;

import com.rivera.learningmanagement.entity.Registration;
import com.rivera.learningmanagement.exception.BusinessException;
import com.rivera.learningmanagement.exception.ErrorModel;
import com.rivera.learningmanagement.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public Registration addRegistration(Registration registration) {
        if (registrationRepository.countSelectedCoursesByStudent(registration.getStudent()) >= 3) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            errorModelList.add(new ErrorModel("COURSES_LIMIT", "Student has already selected three courses."));

            throw new BusinessException(errorModelList);
        }
        return registrationRepository.save(registration);

    }

    public List<Registration> getAllRegistrations() {
        return StreamSupport.stream(registrationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
