package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.exception.ResourceNotFoundException;

import com.smarterfit.model.classSessionBooking.ClassSessionBooking;
import com.smarterfit.model.classSessionBooking.ClassSessionBookingId;
import com.smarterfit.repository.ClassSessionBookingRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ClassSessionBookingValidation {

    private final ClassSessionBookingRepository classSessionBookingRepository;

    public ClassSessionBookingValidation(ClassSessionBookingRepository classSessionBookingRepository) {
        this.classSessionBookingRepository = classSessionBookingRepository;
    }


    public ClassSessionBooking validateClassSessionBookingById(ClassSessionBookingId id ) {
        return  classSessionBookingRepository.findByUserIdAndClassSessionId(id.getUser(), id.getClassSession())
                .orElseThrow(() -> new ResourceNotFoundException("Class session booking not found with id: " + id));
    }

    public void validateClassSessionBookingExists(UUID userId, UUID classSessionId) {
        if (classSessionBookingRepository.existsByUserIdAndClassSessionId(userId, classSessionId)) {
            throw new ResourceAlreadyExistsException("Class session booking already exists");
        }
    }



}
