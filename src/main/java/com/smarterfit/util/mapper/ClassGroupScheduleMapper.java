package com.smarterfit.util.mapper;


import com.smarterfit.dto.request.ClassGroupScheduleRequestDTO;
import com.smarterfit.dto.response.ClassGroupScheduleResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.ClassGroupSchedule;

public class ClassGroupScheduleMapper {

    private ClassGroupScheduleMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassGroupSchedule toEntity(ClassGroupScheduleRequestDTO classGroupScheduleRequestDTO,
                                              ClassGroupSchedule classGroupSchedule, ClassGroup classGroup) {

        if(classGroupScheduleRequestDTO == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }

        classGroupSchedule.setClassGroup(classGroup);
        classGroupSchedule.setDaysOfWeek(classGroupScheduleRequestDTO.daysOfWeek());
        classGroupSchedule.setStartTime(classGroupScheduleRequestDTO.startTime());
        classGroupSchedule.setEndTime(classGroupScheduleRequestDTO.endTime());
        return classGroupSchedule;

    }

    public static ClassGroupSchedule toEntity(ClassGroupScheduleRequestDTO classGroupScheduleRequestDTO,
                                              ClassGroup classGroup) {
        return toEntity(classGroupScheduleRequestDTO, new ClassGroupSchedule(), classGroup);
    }

    public static ClassGroupScheduleResponseDTO toResponse(ClassGroupSchedule classGroupSchedule) {
        return new ClassGroupScheduleResponseDTO(
                classGroupSchedule.getId(),
                classGroupSchedule.getClassGroup().getId(),
                classGroupSchedule.getDaysOfWeek(),
                classGroupSchedule.getStartTime().toString(),
                classGroupSchedule.getEndTime().toString()
        );
    }
}
