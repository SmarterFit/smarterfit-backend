package com.smarterfit.modules.classgroup.service;

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
    public ClassEventBookingResponseDTO createClassEventBooking(ClassEventBookingRequestDTO classEventBookingDTO) {
        ClassEvent classEvent = classEventValidation.validateClassEventById(classEventBookingDTO.classEventId());

        classEventService.incrementBooking(classEvent);

        classEventBookingValidation.validateClassEventBookingExists(classEventBookingDTO.userId(), classEventBookingDTO.classEventId());
        User user = userValidation.validateUserById(classEventBookingDTO.userId());

        ClassEventBooking classEventBooking = ClassEventBookingMapper.toEntity(classEventBookingDTO, classEvent, user);
        ClassEventBooking savedClassEventBooking = classEventBookingRepository.save(classEventBooking);

        return ClassEventBookingMapper.toResponse(savedClassEventBooking);
    }

    @Transactional(readOnly = true)
    public ClassEventBookingResponseDTO getClassEventBookingById(UUID userId, UUID classEventId) {
        ClassEventBookingId classEventBookingId = new ClassEventBookingId(userId, classEventId);
        ClassEventBooking classEventBooking = classEventBookingValidation.validateClassEventBookingById(classEventBookingId);

        return ClassEventBookingMapper.toResponse(classEventBooking);
    }

    @Transactional
    public ClassEventBookingResponseDTO updateClassEventBookingById(ClassEventBookingStatusDTO statusDTO) {
        ClassEventBookingId classEventBookingId = new ClassEventBookingId(statusDTO.userId(), statusDTO.classEventId());
        ClassEventBooking classEventBooking = classEventBookingValidation.validateClassEventBookingById(classEventBookingId);

        ClassEventBooking updatedClassEventBooking = ClassEventBookingMapper.toEntityUpdateStatus(statusDTO, classEventBooking);

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
