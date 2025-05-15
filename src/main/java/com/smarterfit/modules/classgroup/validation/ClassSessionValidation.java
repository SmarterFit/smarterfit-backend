package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classsession.UpdateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.entity.ClassSession;
import com.smarterfit.modules.classgroup.repository.ClassSessionRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassSessionValidation {

    private final ClassSessionRepository classSessionRepository;

    public ClassSessionValidation(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public ClassSession validateClassSessionById(UUID id) {
        return classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found."));
    }

    public void validateClassSessionExists(UUID id) {
        if (classSessionRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Class session already exists");
        }
    }

    public void existsByDateRangeAndClassGroupId(CreateClassSessionRequestDTO dto){
        if(classSessionRepository.existsByDateRangeAndClassGroupId(dto.getClassGroupId(),
                dto.getStartTime(),
                dto.getEndTime())){

                throw new ResourceAlreadyExistsException("A class session already exists at this time.");

        }
    }

    public void existsByDateRangeAndClassGroupId(UpdateClassSessionRequestDTO dto, UUID sessionId, UUID classId){
        if(classSessionRepository.existsByDateRangeAndClassGroupId(classId,
                dto.getStartTime(),
                dto.getEndTime(),
                sessionId)){

            throw new ResourceAlreadyExistsException("A class session already exists at this time.");

        }
    }
}
