package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ClassSessionRequestDTO;
import com.smarterfit.dto.response.ClassSessionResponseDTO;
import com.smarterfit.enums.Status;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.ClassSession;
import com.smarterfit.util.Converter;

public class ClassSessionMapper {

    private ClassSessionMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassSession toEntity(ClassSessionRequestDTO classSessionRequest, ClassSession classSession,  ClassGroup classGroup) {
        if(classSession == null){
            throw new ResourceNotFoundException("ClassSession not found.");
        }
        if (classGroup == null) {
            throw new ResourceNotFoundException("ClassGroup not found.");
        }

        classSession.setClassGroup(classGroup);
        classSession.setStartTime(classSessionRequest.startTime());
        classSession.setEndTime(classSessionRequest.endTime());
        classSession.setStatus(Converter.stringToEnum(Status.class, classSessionRequest.BookingStatus()));

        return classSession;
    }

    public static ClassSession toEntity(ClassSessionRequestDTO classSessionRequest, ClassGroup classGroup) {
        return toEntity(classSessionRequest, new ClassSession(), classGroup);
    }

    public static ClassSessionResponseDTO toResponseDTO(ClassSession classSession) {
        return new ClassSessionResponseDTO(
                classSession.getId(),
                classSession.getClassGroup().getId(),
                classSession.getStartTime(),
                classSession.getEndTime(),
                classSession.getStatus().toString()
        );
    }

}
