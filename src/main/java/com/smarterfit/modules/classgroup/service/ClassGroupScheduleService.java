package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.classgroup.dto.request.classgroup.schedule.CreateClassGroupScheduleRequestDTO;
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

    public ClassGroupScheduleResponseDTO createClassGroupSchedule(CreateClassGroupScheduleRequestDTO requestDTO) {
        classGroupScheduleValidation.validateClassSchedulesDates(requestDTO.getStartTime(), requestDTO.getEndTime());
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        ClassGroupSchedule classGroupSchedule = ClassGroupScheduleMapper.toEntity(requestDTO, classGroup);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(classGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    public ClassGroupScheduleResponseDTO getClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        return ClassGroupScheduleMapper.toResponse(classGroupSchedule);
    }

    public ClassGroupScheduleResponseDTO updateClassGroupScheduleById(UUID id,
            CreateClassGroupScheduleRequestDTO requestDTO) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);

        ClassGroupSchedule updatedClassGroupSchedule = ClassGroupScheduleMapper.toEntity(requestDTO,
                classGroup, classGroupSchedule);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(updatedClassGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    public void deleteClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        classGroupScheduleRepository.delete(classGroupSchedule);
    }
}
