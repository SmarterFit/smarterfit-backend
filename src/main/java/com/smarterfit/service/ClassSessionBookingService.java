package com.smarterfit.service;

import com.smarterfit.dto.request.ClassSessionBookingRequestDTO;
import com.smarterfit.dto.request.ClassSessionBookingStatusDTO;
import com.smarterfit.dto.response.ClassSessionBookingResponseDTO;
import com.smarterfit.model.ClassSession;
import com.smarterfit.model.User;
import com.smarterfit.model.classSessionBooking.ClassSessionBooking;
import com.smarterfit.model.classSessionBooking.ClassSessionBookingId;
import com.smarterfit.repository.ClassSessionBookingRepository;
import com.smarterfit.util.mapper.ClassSessionBookingMapper;
import com.smarterfit.util.validation.ClassSessionBookingValidation;
import com.smarterfit.util.validation.ClassSessionValidation;
import com.smarterfit.util.validation.UserValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClassSessionBookingService {
    private final ClassSessionBookingRepository classSessionBookingRepository;
    private final ClassSessionBookingValidation classSessionBookingValidation;
    private final ClassSessionValidation classSessionValidation;
    private final UserValidation userValidation;

    public ClassSessionBookingService(ClassSessionBookingRepository classSessionBookingRepository,
                                      ClassSessionBookingValidation classSessionBookingValidation,
                                      ClassSessionValidation classSessionValidation, UserValidation userValidation) {
        this.classSessionBookingRepository = classSessionBookingRepository;
        this.classSessionBookingValidation = classSessionBookingValidation;
        this.classSessionValidation = classSessionValidation;
        this.userValidation = userValidation;
    }

    public ClassSessionBookingResponseDTO createClassSessionBooking(ClassSessionBookingRequestDTO classSessionBookingDTO) {
        classSessionBookingValidation.validateClassSessionBookingExists(classSessionBookingDTO.userId(), classSessionBookingDTO.classSessionId());
        ClassSession classSession = classSessionValidation.validateClassSessionById(classSessionBookingDTO.classSessionId());
        User user = userValidation.validateUserById(classSessionBookingDTO.userId());

        ClassSessionBooking classSessionBooking = ClassSessionBookingMapper.toEntity(classSessionBookingDTO, classSession, user);
        ClassSessionBooking savedClassSessionBooking = classSessionBookingRepository.save(classSessionBooking);

        return ClassSessionBookingMapper.toResponse(savedClassSessionBooking);
    }

    public ClassSessionBookingResponseDTO getClassSessionBookingById(UUID userId, UUID classSessionId) {
        ClassSessionBookingId classSessionBookingId = new ClassSessionBookingId(userId, classSessionId);
        ClassSessionBooking classSessionBooking = classSessionBookingValidation.validateClassSessionBookingById(classSessionBookingId);

        return ClassSessionBookingMapper.toResponse(classSessionBooking);
    }

    public ClassSessionBookingResponseDTO updateClassSessionBookingById(ClassSessionBookingStatusDTO statusDTO) {
        ClassSessionBookingId classSessionBookingId = new ClassSessionBookingId(statusDTO.userId(), statusDTO.classSessionId());
        ClassSessionBooking classSessionBooking = classSessionBookingValidation.validateClassSessionBookingById(classSessionBookingId);

        ClassSessionBooking updatedClassSessionBooking = ClassSessionBookingMapper.toEntityUpdateStatus(statusDTO, classSessionBooking);

        return ClassSessionBookingMapper.toResponse(classSessionBookingRepository.save(updatedClassSessionBooking));
    }

    public List<ClassSessionBookingResponseDTO> getAllBookingsToClassSession(UUID classSessionId) {
        return classSessionBookingRepository.findByClassSessionId(classSessionId).stream()
                .map(ClassSessionBookingMapper::toResponse)
                .toList();
    }


}
