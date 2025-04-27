package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.classgroup.dto.request.classevent.CreateClassEventRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassEvent;
import com.smarterfit.modules.classgroup.entity.ClassGroup;

public class ClassEventMapper {

    private ClassEventMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassEvent toEntity(CreateClassEventRequestDTO dto, ClassGroup classGroup) {
        return toEntity(dto, classGroup, new ClassEvent());
    }

    public static ClassEvent toEntity(CreateClassEventRequestDTO dto, ClassGroup classGroup, ClassEvent classEvent) {
        if (classEvent == null) {
            throw new ResourceNotFoundException("ClassEvent not found.");
        }
        if (classGroup == null) {
            throw new ResourceNotFoundException("ClassGroup not found.");
        }

        classEvent = GenericMapper.map(dto, classEvent);
        classEvent.setClassGroup(classGroup);

        return classEvent;
    }

    public static ClassEventResponseDTO toResponse(ClassEvent classEvent) {

        return new ClassEventResponseDTO(
                classEvent.getId(),
                classEvent.getClassGroup().getId(),
                classEvent.getCapacity(),
                classEvent.getBookingCount(),
                classEvent.getStartDate(),
                classEvent.getEndDate(),
                classEvent.getStatus());
    }

}
