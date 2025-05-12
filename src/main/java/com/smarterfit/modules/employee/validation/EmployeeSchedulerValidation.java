package com.smarterfit.modules.employee.validation;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.employee.dto.request.schedule.EmployeeSchedulerRequestDTO;
import com.smarterfit.modules.employee.entity.EmployeeSchedule;
import com.smarterfit.modules.employee.repository.EmployeeSchedulerRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Component
public class EmployeeSchedulerValidation {

    public final EmployeeSchedulerRepository employeeSchedulerRepository;

    public EmployeeSchedulerValidation(EmployeeSchedulerRepository employeeSchedulerRepository) {
        this.employeeSchedulerRepository = employeeSchedulerRepository;
    }


    public EmployeeSchedule validateEmployeeScheduleById(UUID id) {
        return employeeSchedulerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee schedule not found."));
    }

    public boolean validateNoScheduleConflict(UUID employeeId, EmployeeSchedulerRequestDTO dto) {
        boolean existsConflict = employeeSchedulerRepository.existsOverlappingSchedule(
                employeeId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());

        if (existsConflict) {
            throw new IllegalArgumentException("Conflict with existing schedule.");
        }

        return false;
    }

    public void validateEmployeeSchedule(LocalTime startDate, LocalTime endDate) {
        DateValidation.validateTimeRange(startDate, endDate);
    }

}
