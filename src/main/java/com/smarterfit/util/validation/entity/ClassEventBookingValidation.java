package com.smarterfit.util.validation.entity;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.exception.ResourceNotFoundException;

import com.smarterfit.model.classEventBooking.ClassEventBooking;
import com.smarterfit.model.classEventBooking.ClassEventBookingId;
import com.smarterfit.repository.ClassEventBookingRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ClassEventBookingValidation {

    private final ClassEventBookingRepository classEventBookingRepository;

    public ClassEventBookingValidation(ClassEventBookingRepository classEventBookingRepository) {
        this.classEventBookingRepository = classEventBookingRepository;
    }


    public ClassEventBooking validateClassEventBookingById(ClassEventBookingId id ) {
        return  classEventBookingRepository.findByUserIdAndClassEventId(id.getUser(), id.getClassEvent())
                .orElseThrow(() -> new ResourceNotFoundException("Class session booking not found with id: " + id));
    }

    public void validateClassEventBookingExists(UUID userId, UUID classEventId) {
        if (classEventBookingRepository.existsByUserIdAndClassEventId(userId, classEventId)) {
            throw new ResourceAlreadyExistsException("Class session booking already exists");
        }
    }



}
