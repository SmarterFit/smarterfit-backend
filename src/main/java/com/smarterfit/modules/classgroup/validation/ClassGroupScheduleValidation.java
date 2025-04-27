package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.classgroup.entity.ClassGroupSchedule;
import com.smarterfit.modules.classgroup.repository.ClassGroupScheduleRepository;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

@Component
public class ClassGroupScheduleValidation {

    private final ClassGroupScheduleRepository classGroupScheduleRepository;

    public ClassGroupScheduleValidation(ClassGroupScheduleRepository classGroupScheduleRepository) {
        this.classGroupScheduleRepository = classGroupScheduleRepository;
    }

    public ClassGroupSchedule validateClassGroupScheduleById(UUID id) {
        return classGroupScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group  schedule not found."));
    }

    public boolean validateNoScheduleConflict(UUID classGroupId, Integer dayOfWeek, LocalTime startTime,
            LocalTime endTime) {
        boolean existsConflict = classGroupScheduleRepository.existsOverlappingSchedule(
                classGroupId, dayOfWeek, startTime, endTime);

        if (existsConflict) {
            throw new IllegalArgumentException("A class is already scheduled for this group at the same time.");
        }

        return false;
    }

    public void validateClassSchedulesDates(LocalTime startDate, LocalTime endDate) {
        DateValidation.validateTimeRange(startDate, endDate);
    }

}
