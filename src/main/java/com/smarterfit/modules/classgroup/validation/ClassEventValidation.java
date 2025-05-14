package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.classgroup.dto.request.classevent.CreateClassEventRequestDTO;
import com.smarterfit.modules.classgroup.entity.ClassEvent;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.repository.ClassEventRepository;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ClassEventValidation {

    private final ClassEventRepository classEventRepository;

    public ClassEventValidation(ClassEventRepository classEventRepository) {
        this.classEventRepository = classEventRepository;
    }

    public ClassEvent validateClassEventById(UUID id) {
        return classEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }

    public void validateClassEventExists(UUID classEventId) {
        classEventRepository.findById(classEventId)
                .ifPresent(classEvent -> {
                    throw new ResourceAlreadyExistsException("Class event already exists.");
                });

    }

    public void validateEventTimeConflict(UUID classGroupId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean eventExists = classEventRepository
                .existsByClassGroupIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        classGroupId, endDate, startDate);

        if (eventExists) {
            throw new ResourceAlreadyExistsException("A class event already exists at this time.");
        }
    }

    public void validateEventTimeConflict(UUID classGroupId, LocalDateTime startDate, LocalDateTime endDate, UUID eventID) {
        boolean hasConflict = classEventRepository.existsByDateRangeAndClassGroupExceptCurrent(
                startDate,
                endDate,
                classGroupId,
                eventID
        );

        if (hasConflict) {
            throw new ResourceAlreadyExistsException("A class event already exists at this time.");
        }
    }

    public void validateClassEventDates(LocalDateTime startDate, LocalDateTime endDate) {
        DateValidation.validateDateTimeRange(startDate, endDate, Boolean.TRUE);
    }

    public void validateBookingCount(Integer bookingCount, Integer maxBookingCount) {
        if (bookingCount >= maxBookingCount) {
            throw new ResourceAlreadyExistsException("Class event is fully booked.");
        }
    }

    public void validateDates(CreateClassEventRequestDTO requestDTO, ClassGroup classGroup) {
        validateClassEventDates(requestDTO.getStartDate(), requestDTO.getEndDate());
        validateEventTimeConflict(classGroup.getId(), requestDTO.getStartDate(),
                requestDTO.getEndDate());
    }

    public void validateDates(CreateClassEventRequestDTO requestDTO, ClassGroup classGroup, UUID eventID) {
        validateClassEventDates(requestDTO.getStartDate(), requestDTO.getEndDate());
        validateEventTimeConflict(classGroup.getId(), requestDTO.getStartDate(),
                requestDTO.getEndDate());
    }

    public void validateNoBookings(Integer bookingCount) {
        if (bookingCount != null && bookingCount > 0) {
            throw new ResourceAlreadyExistsException("Cannot delete class event: it already has bookings.");
        }
    }

}
