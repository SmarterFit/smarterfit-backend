package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.SessionStatus;
import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classsession.UpdateClassSessionRequestDTO;
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
        classSessionValidation.existsByDateRangeAndClassGroupId(requestDTO);

        ClassSession classSession = ClassSessionMapper.toEntity(requestDTO, classGroup);
        classSessionRepository.save(classSession);

        return ClassSessionMapper.toResponse(classSession);
    }

    @Transactional(readOnly = true)
    public ClassSessionResponseDTO getClassSessionById(UUID id) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        return ClassSessionMapper.toResponse(classSession);
    }

    @Transactional(readOnly = true)
    public List<ClassSessionResponseDTO> getAllClassSessionByGroup(UUID classGroupId) {
        return classSessionRepository.findAllSessionsByClassGroupId(classGroupId).stream()
                .map(ClassSessionMapper::toResponse)
                .toList();
    }

    @Transactional
    public ClassSessionResponseDTO updateClassSessionById(UUID id, UpdateClassSessionRequestDTO requestDTO) {
        ClassSession classSession = classSessionValidation.validateClassSessionById(id);
        classSessionValidation.existsByDateRangeAndClassGroupId(requestDTO, id, classSession.getClassGroup().getId());

        classSession = ClassSessionMapper.toEntity(requestDTO, classSession);
        classSessionRepository.save(classSession);

        return ClassSessionMapper.toResponse(classSession);
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

            boolean sessionExists = classGroupScheduleValidation.validateNoScheduleConflict(schedule);

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
