package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.EventStatus;
import com.smarterfit.modules.classgroup.dto.request.classevent.CreateClassEventRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassEvent;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.mapper.ClassEventMapper;
import com.smarterfit.modules.classgroup.repository.ClassEventRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClassEventService {

    private final ClassEventRepository classEventRepository;
    private final ValidationFaced validationFaced;

    public ClassEventService(ClassEventRepository classEventRepository,
            ValidationFaced validationFaced) {
        this.classEventRepository = classEventRepository;
        this.validationFaced = validationFaced;
    }

    @Transactional
    public ClassEventResponseDTO createClassEvent(CreateClassEventRequestDTO requestDTO) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());
        validationFaced.classEventValidation.validateDates(requestDTO, classGroup);
        // TODO: validar conflitos de horário com o instrutor

        ClassEvent classEvent = ClassEventMapper.toEntity(requestDTO, classGroup);
        classEventRepository.save(classEvent);

        return ClassEventMapper.toResponse(classEvent);
    }

    @Transactional(readOnly = true)
    public ClassEventResponseDTO getClassEventById(UUID id) {
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(id);
        return ClassEventMapper.toResponse(classEvent);
    }

    @Transactional(readOnly = true)
    // TODO: incluir filtros por: modalidade, tipo, data, disponibilidade (função separada (search))
    public List<ClassEventResponseDTO> getAllClassEvents() {
        return classEventRepository.findAllUnfinishedEvents().stream().map(ClassEventMapper::toResponse).toList();
    }

    @Transactional
    public void updateClassEventById(UUID classEventId, CreateClassEventRequestDTO requestDTO) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(classEventId);

        validationFaced.classEventValidation.validateDates(requestDTO, classGroup, classEventId);
        ClassEvent updatedClassEvent = ClassEventMapper.toEntity(requestDTO,  classGroup, classEvent);

        classEventRepository.save(updatedClassEvent);

        ClassEventMapper.toResponse(updatedClassEvent);
    }

    /// TODO: softdelete
    @Transactional
    public void deleteClassEventById(UUID classEventId) {
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(classEventId);

        validationFaced.classEventValidation.validateNoBookings(classEvent.getBookingCount());
        classEventRepository.delete(classEvent);
    }

    @Transactional
    public void cancelClassEventById(UUID classEventId) {
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(classEventId);
        classEvent.setStatus(EventStatus.CANCELED);
        classEventRepository.save(classEvent);

    }

    @Transactional()
    public void updateFinishedEvents() {
        List<ClassEvent> events = classEventRepository.findAllByFinishedFalse();
        for (ClassEvent event : events) {
            if(event.getEndDate().isBefore(LocalDateTime.now())){
                event.setFinished(true);
            }
        }
        classEventRepository.saveAll(events);
    }


    public void incrementBooking(ClassEvent classEvent) {
        validationFaced.classEventValidation.validateBookingCount(classEvent.getBookingCount(),
                classEvent.getCapacity());
        classEvent.setBookingCount(classEvent.getBookingCount() + 1);
        classEventRepository.save(classEvent);
    }

    public void decrementBooking(ClassEvent classEvent) {
        classEvent.setBookingCount(classEvent.getBookingCount() - 1);
        classEventRepository.save(classEvent);
    }
}
