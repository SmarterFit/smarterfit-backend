package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.classgroup.dto.request.classgroup.schedule.CreateClassGroupScheduleRequestDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroupSchedule;
import com.smarterfit.modules.classgroup.repository.ClassGroupScheduleRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
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

    public boolean validateNoScheduleConflict(ClassGroupSchedule schedule) {
        boolean existsConflict = classGroupScheduleRepository.existsOverlappingSchedule(
                schedule.getClassGroup().getId(),
                schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime());

        return existsScheduleConflict(existsConflict);
    }

    public boolean validateNoScheduleConflict(CreateClassGroupScheduleRequestDTO dto) {
        boolean existsConflict = classGroupScheduleRepository.existsOverlappingSchedule(
                dto.getClassGroupId(), dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());

        return existsScheduleConflict(existsConflict);

    }

    public boolean existsScheduleConflict(boolean existsConflict){
        if (existsConflict) {
            throw new IllegalArgumentException("A class is already scheduled for this group at the same time.");
        }
        return existsConflict;
    }

    public void validateClassSchedulesDates(LocalTime startDate, LocalTime endDate) {
        DateValidation.validateTimeRange(startDate, endDate);
    }

}
