package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.enums.SessionStatus;
import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupSchedule;
import com.smarterfit.modules.classgroup.entity.ClassSession;
import com.smarterfit.modules.classgroup.mapper.ClassSessionMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupScheduleRepository;
import com.smarterfit.modules.classgroup.repository.ClassSessionRepository;
import com.smarterfit.modules.classgroup.validation.ClassGroupScheduleValidation;
import com.smarterfit.modules.classgroup.validation.ClassGroupValidation;
import com.smarterfit.modules.classgroup.validation.ClassSessionValidation;

import com.smarterfit.modules.useraccess.validation.RolesValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final ClassGroupValidation classGroupValidation;
    private final ClassSessionValidation classSessionValidation;
    private final ClassGroupScheduleValidation classGroupScheduleValidation;
    private final ClassGroupScheduleRepository classGroupScheduleRepository;

    public ClassSessionService(ClassSessionRepository classSessionRepository, ClassGroupValidation classGroupValidation,
            ClassSessionValidation classSessionValidation,
            ClassGroupScheduleValidation classGroupScheduleValidation,
            ClassGroupScheduleRepository classGroupScheduleRepository) {

        this.classSessionRepository = classSessionRepository;
        this.classGroupValidation = classGroupValidation;
        this.classSessionValidation = classSessionValidation;
        this.classGroupScheduleValidation = classGroupScheduleValidation;
        this.classGroupScheduleRepository = classGroupScheduleRepository;
    }

    @Transactional
    public ClassSessionResponseDTO createClassSession(CreateClassSessionRequestDTO requestDTO) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        ClassSession classSession = ClassSessionMapper.toEntity(requestDTO, classGroup);
        classSessionRepository.save(classSession);

        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional(readOnly = true)
    public ClassSessionResponseDTO getClassSessionById(UUID id) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional(readOnly = true)
    public List<ClassSessionResponseDTO> getAllClassSession() {
        return classSessionRepository.findAll().stream()
                .map(ClassSessionMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public ClassSessionResponseDTO updateClassSessionById(UUID id, CreateClassSessionRequestDTO requestDTO) {

        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        ClassSessionMapper.toEntity(requestDTO, classGroup, classSession);
        classSessionRepository.save(classSession);

        return ClassSessionMapper.toResponseDTO(classSession);
    }

    @Transactional
    public void deleteClassSessionById(UUID id) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        classSessionRepository.delete(classSession);
    }

    public void generateDailySessions() {
        LocalDate today = LocalDate.now();
        List<ClassGroupSchedule> schedules = classGroupScheduleRepository.findValidSchedules(today);

        for (ClassGroupSchedule schedule : schedules) {

            boolean sessionExists = classGroupScheduleValidation.validateNoScheduleConflict(
                    schedule.getClassGroup().getId(),
                    schedule.getDayOfWeek(),
                    schedule.getStartTime(),
                    schedule.getEndTime());

            if (!sessionExists) {
                LocalDateTime startDateTime = today.atTime(schedule.getStartTime());
                LocalDateTime endDateTime = today.atTime(schedule.getEndTime());

                ClassSession session = new ClassSession();
                session.setClassGroup(schedule.getClassGroup());
                session.setStartTime(startDateTime);
                session.setEndTime(endDateTime);
                session.setStatus(SessionStatus.SCHEDULED);

                classSessionRepository.save(session);
            }
        }
    }

}
