package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.EventStatus;
import com.smarterfit.modules.classgroup.dto.request.classevent.ClassEventRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassEvent;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.mapper.ClassEventMapper;
import com.smarterfit.modules.classgroup.repository.ClassEventRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ClassEventResponseDTO createClassEvent(ClassEventRequestDTO classEventRequest) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classEventRequest.classGroupId());
        validateDates(classEventRequest, classGroup);
        // TODO: validar conflitos de horário com o instrutor

        ClassEvent classEvent = ClassEventMapper.toEntity(classEventRequest, classGroup);
        classEventRepository.save(classEvent);

        return ClassEventMapper.toResponse(classEvent);
    }

    @Transactional(readOnly = true)
    public ClassEventResponseDTO getClassEventById(UUID id) {
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(id);
        return ClassEventMapper.toResponse(classEvent);
    }

    @Transactional(readOnly = true)
    // TODO: incluir filtros por: modalidade, tipo, data, disponibilidade
    public List<ClassEventResponseDTO> getAllClassEvents() {
        return classEventRepository.findAll().stream().map(ClassEventMapper::toResponse).
                toList();
    }

    @Transactional
    public ClassEventResponseDTO updateClassEventById(UUID classEventId, ClassEventRequestDTO classEventRequest) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classEventRequest.classGroupId());
        validateDates(classEventRequest, classGroup);
        ClassEvent classEvent = validationFaced.classEventValidation.validateClassEventById(classEventId);

        classEvent = ClassEventMapper.toEntity(classEventRequest, classEvent, classGroup);
        classEventRepository.save(classEvent);
        return ClassEventMapper.toResponse(classEvent);
    }


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

    private void validateDates(ClassEventRequestDTO classEventRequest, ClassGroup classGroup) {
        validationFaced.classEventValidation.validateClassEventDates(classEventRequest.startDate(), classEventRequest.endDate());
        validationFaced.classEventValidation.validateEventTimeConflict(classGroup.getId(), classEventRequest.startDate(), classEventRequest.endDate());
    }

    public void incrementBooking(ClassEvent classEvent) {

        validationFaced.classEventValidation.validateBookingCount(classEvent.getBookingCount(), classEvent.getCapacity());
        classEvent.setBookingCount(classEvent.getBookingCount() + 1);
        classEventRepository.save(classEvent);
    }

    public void decrementBooking(ClassEvent classEvent) {
        classEvent.setBookingCount(classEvent.getBookingCount() - 1);
        classEventRepository.save(classEvent);
    }




}
