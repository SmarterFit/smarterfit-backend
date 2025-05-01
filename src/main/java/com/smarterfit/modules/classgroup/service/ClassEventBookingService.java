package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.classgroup.dto.request.classevent.booking.CreateClassEventBookingRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classevent.booking.UpdateClassEventBookingRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventBookingResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassEvent;
import com.smarterfit.modules.classgroup.entity.ClassEventBooking;
import com.smarterfit.modules.classgroup.entity.id.ClassEventBookingId;
import com.smarterfit.modules.classgroup.mapper.ClassEventBookingMapper;
import com.smarterfit.modules.classgroup.repository.ClassEventBookingRepository;
import com.smarterfit.modules.classgroup.validation.ClassEventBookingValidation;
import com.smarterfit.modules.classgroup.validation.ClassEventValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClassEventBookingService {
    private final ClassEventService classEventService;
    private final ClassEventBookingRepository classEventBookingRepository;
    private final ClassEventBookingValidation classEventBookingValidation;
    private final ClassEventValidation classEventValidation;
    private final UserValidation userValidation;

    public ClassEventBookingService(ClassEventBookingRepository classEventBookingRepository,
            ClassEventBookingValidation classEventBookingValidation,
            ClassEventValidation classEventValidation, UserValidation userValidation,
            ClassEventService classEventService) {
        this.classEventBookingRepository = classEventBookingRepository;
        this.classEventBookingValidation = classEventBookingValidation;
        this.classEventValidation = classEventValidation;
        this.userValidation = userValidation;
        this.classEventService = classEventService;
    }

    @Transactional
    public ClassEventBookingResponseDTO createClassEventBooking(CreateClassEventBookingRequestDTO requestDTO) {
        ClassEvent classEvent = classEventValidation.validateClassEventById(requestDTO.getClassEventId());

        classEventService.incrementBooking(classEvent);

        classEventBookingValidation.validateClassEventBookingExists(requestDTO.getUserId(),
                requestDTO.getClassEventId());
        User user = userValidation.validateUserById(requestDTO.getUserId());

        ClassEventBooking classEventBooking = ClassEventBookingMapper.toEntity(requestDTO, classEvent, user);
        ClassEventBooking savedClassEventBooking = classEventBookingRepository.save(classEventBooking);

        return ClassEventBookingMapper.toResponse(savedClassEventBooking);
    }

    @Transactional(readOnly = true)
    public ClassEventBookingResponseDTO getClassEventBookingById(UUID userId, UUID classEventId) {
        ClassEventBookingId classEventBookingId = new ClassEventBookingId(userId, classEventId);
        ClassEventBooking classEventBooking = classEventBookingValidation
                .validateClassEventBookingById(classEventBookingId);

        return ClassEventBookingMapper.toResponse(classEventBooking);
    }

    @Transactional
    public ClassEventBookingResponseDTO updateClassEventBookingById(UpdateClassEventBookingRequestDTO requestDTO) {
        ClassEventBookingId classEventBookingId = new ClassEventBookingId(requestDTO.getUserId(),
                requestDTO.getClassEventId());
        ClassEventBooking classEventBooking = classEventBookingValidation
                .validateClassEventBookingById(classEventBookingId);

        ClassEventBooking updatedClassEventBooking = ClassEventBookingMapper.toEntityUpdateStatus(requestDTO,
                classEventBooking);

        classEventService.decrementBooking(classEventBooking.getClassEvent());
        return ClassEventBookingMapper.toResponse(classEventBookingRepository.save(updatedClassEventBooking));
    }

    @Transactional(readOnly = true)
    public List<ClassEventBookingResponseDTO> getAllBookingsToClassEvent(UUID classEventId) {
        return classEventBookingRepository.findByClassEventId(classEventId).stream()
                .map(ClassEventBookingMapper::toResponse)
                .toList();
    }
}
