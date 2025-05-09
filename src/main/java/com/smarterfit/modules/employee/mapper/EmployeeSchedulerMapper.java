package com.smarterfit.modules.employee.mapper;

import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.employee.dto.request.schedule.EmployeeSchedulerRequestDTO;
import com.smarterfit.modules.employee.dto.response.EmployeeScheduleResponseDTO;
import com.smarterfit.modules.employee.entity.EmployeeSchedule;

public class EmployeeSchedulerMapper {

    private EmployeeSchedulerMapper() {
        // Private constructor to prevent instantiation
    }

    public static EmployeeSchedule toEntity(EmployeeSchedulerRequestDTO dto) {
        return toEntity(dto, new EmployeeSchedule());
    }

    public static EmployeeSchedule toEntity(EmployeeSchedulerRequestDTO dto, EmployeeSchedule employeeSchedule) {

        if (employeeSchedule == null) {
            throw new IllegalArgumentException("EmployeeSchedule not found");
        }
        employeeSchedule = GenericMapper.map(dto, employeeSchedule);

        return employeeSchedule;
    }

    public static EmployeeScheduleResponseDTO toResponse (EmployeeSchedule employeeSchedule) {
        if (employeeSchedule == null) {
            throw new IllegalArgumentException("EmployeeSchedule not found");
        }

        return GenericMapper.map(employeeSchedule, EmployeeScheduleResponseDTO.class);
    }

}
