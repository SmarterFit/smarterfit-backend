package com.smarterfit.util.validation.entity;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassEvent;
import com.smarterfit.repository.ClassEventRepository;
import com.smarterfit.util.validation.DateValidationUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ClassEventValidation {

    private final ClassEventRepository classEventRepository;

    public ClassEventValidation(ClassEventRepository classEventRepository) {
        this.classEventRepository = classEventRepository;
    }


    public ClassEvent validateClassEventById(UUID id) {
        return  classEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }

    public void validateClassEventExists(UUID classEventId) {
        classEventRepository.findById(classEventId)
                .ifPresent(classEvent -> {
                    throw new ResourceAlreadyExistsException("Class event already exists.");
                });

    }

    public void validateEventTimeConflict(UUID classGroupId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean eventExists = classEventRepository.existsByClassGroupIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                classGroupId, endDate, startDate);

        if (eventExists) {
            throw new ResourceAlreadyExistsException("A class event already exists at this time.");
        }
    }

    public void validateClassEventDates(LocalDateTime startDate, LocalDateTime endDate) {
        DateValidationUtils.validateDateTimeRange(startDate, endDate, Boolean.TRUE);
    }

    public void validateBookingCount(Integer bookingCount, Integer maxBookingCount) {
        if (bookingCount >= maxBookingCount) {
            throw new ResourceAlreadyExistsException("Class event is fully booked.");
        }
    }

    public void validateNoBookings(Integer bookingCount) {
        if (bookingCount != null && bookingCount > 0) {
            throw new ResourceAlreadyExistsException("Cannot delete class event: it already has bookings.");
        }
    }


}
