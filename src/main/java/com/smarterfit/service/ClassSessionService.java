package com.smarterfit.service;


import com.smarterfit.dto.request.ClassSessionRequestDTO;
import com.smarterfit.dto.response.ClassSessionResponseDTO;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.ClassSession;
import com.smarterfit.repository.ClassSessionRepository;
import com.smarterfit.util.mapper.ClassSessionMapper;
import com.smarterfit.util.validation.ClassGroupValidation;
import com.smarterfit.util.validation.ClassSessionValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final ClassGroupValidation classGroupValidation;
    private final ClassSessionValidation classSessionValidation;

    public ClassSessionService(ClassSessionRepository classSessionRepository, ClassGroupValidation classGroupValidation,
                               ClassSessionValidation classSessionValidation) {
        this.classSessionRepository = classSessionRepository;
        this.classGroupValidation = classGroupValidation;
        this.classSessionValidation = classSessionValidation;
    }


    @Transactional
    public ClassSessionResponseDTO createClassSession(ClassSessionRequestDTO classSessionRequest) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(classSessionRequest.classGroupId());
        ClassSession classSession = ClassSessionMapper.toEntity(classSessionRequest, classGroup);
        classSessionRepository.save(classSession);
        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional(readOnly = true)
    public ClassSessionResponseDTO getClassSessionById(UUID id) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional
    public ClassSessionResponseDTO updateClassSessionById(UUID id, ClassSessionRequestDTO classSessionRequest) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(classSessionRequest.classGroupId());
        ClassSessionMapper.toEntity(classSessionRequest, classSession, classGroup);
        classSessionRepository.save(classSession);
        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional
    public void deleteClassSessionById(UUID id) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        classSessionRepository.delete(classSession);
    }



}
