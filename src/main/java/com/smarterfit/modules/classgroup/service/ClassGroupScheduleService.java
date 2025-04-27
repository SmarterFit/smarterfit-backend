package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.classgroup.dto.request.classgroup.schedule.ClassGroupScheduleRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupScheduleResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupSchedule;
import com.smarterfit.modules.classgroup.mapper.ClassGroupScheduleMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupScheduleRepository;
import com.smarterfit.modules.classgroup.validation.ClassGroupScheduleValidation;
import com.smarterfit.modules.classgroup.validation.ClassGroupValidation;

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
        classGroupScheduleValidation.validateClassSchedulesDates(classGroupScheduleDTO.startTime(), classGroupScheduleDTO.endTime());
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
