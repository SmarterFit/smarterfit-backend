package com.smarterfit.service;


import com.smarterfit.dto.request.ClassSessionRequestDTO;
import com.smarterfit.dto.response.ClassSessionResponseDTO;
import com.smarterfit.enums.Status;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.ClassGroupSchedule;
import com.smarterfit.model.ClassSession;
import com.smarterfit.repository.ClassGroupRepository;
import com.smarterfit.repository.ClassGroupScheduleRepository;
import com.smarterfit.repository.ClassSessionRepository;
import com.smarterfit.util.mapper.ClassSessionMapper;
import com.smarterfit.util.validation.ClassGroupScheduleValidation;
import com.smarterfit.util.validation.ClassGroupValidation;
import com.smarterfit.util.validation.ClassSessionValidation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupValidation classGroupValidation;
    private final ClassSessionValidation classSessionValidation;
    private final ClassGroupScheduleValidation classGroupScheduleValidation;
    private final ClassGroupScheduleRepository classGroupScheduleRepository;

    public ClassSessionService(ClassSessionRepository classSessionRepository, ClassGroupValidation classGroupValidation,
                               ClassSessionValidation classSessionValidation, ClassGroupRepository classGroupRepository,
                               ClassGroupScheduleValidation classGroupScheduleValidation,
                               ClassGroupScheduleRepository classGroupScheduleRepository) {
        this.classSessionRepository = classSessionRepository;
        this.classGroupValidation = classGroupValidation;
        this.classSessionValidation = classSessionValidation;
        this.classGroupRepository = classGroupRepository;
        this.classGroupScheduleValidation = classGroupScheduleValidation;
        this.classGroupScheduleRepository = classGroupScheduleRepository;
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



    @Scheduled(cron = "0 0 2 * * 0") // Executa todos os dias às 02:00
    public void generateDailySessions() {
        List<ClassGroupSchedule> schedules = classGroupScheduleRepository.findAll();
        LocalDate today = LocalDate.now();

        for (ClassGroupSchedule schedule : schedules) {

            boolean sessionExists = classGroupScheduleValidation.validateNoScheduleConflict(
                    schedule.getClassGroup().getId(),
                    schedule.getDayOfWeek(),
                    schedule.getStartTime(),
                    schedule.getEndTime()
            );

            if (!sessionExists) {
                LocalDateTime startDateTime = today.atTime(schedule.getStartTime());
                LocalDateTime endDateTime = today.atTime(schedule.getEndTime());

                ClassSession session = new ClassSession();
                session.setClassGroup(schedule.getClassGroup());
                session.setStartTime(startDateTime);
                session.setEndTime(endDateTime);
                session.setStatus(Status.SCHEDULED);
                session.setCapacity(schedule.getClassGroup().getCapacity());

                classSessionRepository.save(session);
            }

        }
    }


}
