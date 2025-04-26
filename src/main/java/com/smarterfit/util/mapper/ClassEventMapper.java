package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ClassEventRequestDTO;
import com.smarterfit.dto.response.ClassEventResponseDTO;
import com.smarterfit.enums.EventStatus;
import com.smarterfit.enums.Status;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassEvent;
import com.smarterfit.model.ClassGroup;

public class ClassEventMapper {

    private ClassEventMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassEvent toEntity(ClassEventRequestDTO dto, ClassEvent classEvent, ClassGroup classGroup) {
        if (dto == null) {
            throw new ResourceNotFoundException("ClassEvent not found.");
        }
        classEvent.setClassGroup(classGroup);
        classEvent.setCapacity(dto.capacity());
        classEvent.setBookingCount(0);
        classEvent.setStartDate(dto.startDate());
        classEvent.setEndDate(dto.endDate());
        classEvent.setStatus(EventStatus.CONFIRMED); // Default status



        return classEvent;
    }

    public static ClassEvent toEntity(ClassEventRequestDTO dto, ClassGroup classGroup) {
        return toEntity(dto, new ClassEvent(),  classGroup);
    }

    public static ClassEventResponseDTO toResponse(ClassEvent classEvent) {


        return new ClassEventResponseDTO(
                classEvent.getId(),
                classEvent.getClassGroup().getId(),
                classEvent.getCapacity(),
                classEvent.getBookingCount(),
                classEvent.getStartDate(),
                classEvent.getEndDate(),
                classEvent.getStatus()
        );
    }



}
