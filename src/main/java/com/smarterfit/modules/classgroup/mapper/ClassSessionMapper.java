package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassSession;

public class ClassSessionMapper {

    private ClassSessionMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassSession toEntity(CreateClassSessionRequestDTO dto, ClassGroup classGroup) {
        return toEntity(dto, classGroup, new ClassSession());
    }

    public static ClassSession toEntity(CreateClassSessionRequestDTO dto,
            ClassGroup classGroup, ClassSession classSession) {
        if (classSession == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }
        if (classGroup == null) {
            throw new ResourceNotFoundException("ClassGroup not found.");
        }

        classSession = GenericMapper.map(dto, classSession);
        classSession.setClassGroup(classGroup);

        return classSession;
    }

    public static ClassSessionResponseDTO toResponseDTO(ClassSession classSession) {
        if (classSession == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }

        ClassSessionResponseDTO response = GenericMapper.map(classSession, ClassSessionResponseDTO.class);
        response = response.toBuilder().classGroupId(classSession.getClassGroup().getId()).build();

        return response;
    }

}
