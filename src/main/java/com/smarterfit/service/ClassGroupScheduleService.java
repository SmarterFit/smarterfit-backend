package com.smarterfit.service;

import com.smarterfit.dto.request.ClassGroupScheduleRequestDTO;
import com.smarterfit.dto.response.ClassGroupScheduleResponseDTO;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.ClassGroupSchedule;
import com.smarterfit.repository.ClassGroupScheduleRepository;
import com.smarterfit.util.mapper.ClassGroupScheduleMapper;
import com.smarterfit.util.validation.ClassGroupScheduleValidation;
import com.smarterfit.util.validation.ClassGroupValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClassGroupScheduleService {

    private final ClassGroupScheduleRepository classGroupScheduleRepository;
    private final ClassGroupScheduleValidation classGroupScheduleValidation;
    private final ClassGroupValidation classGroupValidation;

    public ClassGroupScheduleService(ClassGroupScheduleRepository classGroupScheduleRepository,
                                      ClassGroupScheduleValidation classGroupScheduleValidation,
                                      ClassGroupValidation classGroupValidation) {
        this.classGroupScheduleRepository = classGroupScheduleRepository;
        this.classGroupScheduleValidation = classGroupScheduleValidation;
        this.classGroupValidation = classGroupValidation;
    }


    public ClassGroupScheduleResponseDTO createClassGroupSchedule(ClassGroupScheduleRequestDTO classGroupScheduleDTO) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(classGroupScheduleDTO.classGroupId());

        ClassGroupSchedule classGroupSchedule = ClassGroupScheduleMapper.toEntity(classGroupScheduleDTO, classGroup);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(classGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    public ClassGroupScheduleResponseDTO getClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        return ClassGroupScheduleMapper.toResponse(classGroupSchedule);
    }

    public ClassGroupScheduleResponseDTO updateClassGroupScheduleById(UUID id, ClassGroupScheduleRequestDTO classGroupScheduleDTO) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(classGroupScheduleDTO.classGroupId());
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);

        ClassGroupSchedule updatedClassGroupSchedule = ClassGroupScheduleMapper.toEntity(classGroupScheduleDTO,
                classGroupSchedule, classGroup);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(updatedClassGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    public void deleteClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        classGroupScheduleRepository.delete(classGroupSchedule);
    }
}
