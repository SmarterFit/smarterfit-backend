package com.smarterfit.modules.checkin.service;

import java.util.List;
import java.util.UUID;

import com.smarterfit.modules.checkin.event.CalculatePointsUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.dto.request.ClassCheckInRequestDTO;
import com.smarterfit.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.smarterfit.modules.checkin.entity.ClassCheckIn;
import com.smarterfit.modules.checkin.entity.id.ClassCheckInId;
import com.smarterfit.modules.checkin.mapper.ClassCheckInMapper;
import com.smarterfit.modules.checkin.repository.ClassCheckInRepository;
import com.smarterfit.modules.checkin.util.SensitiveCheckInDataDecryptor;
import com.smarterfit.modules.checkin.validation.ClassCheckInValidation;
import com.smarterfit.modules.classgroup.entity.ClassSession;
import com.smarterfit.modules.classgroup.validation.ClassGroupUserValidation;
import com.smarterfit.modules.classgroup.validation.ClassSessionValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class ClassCheckInService {
   private final ClassCheckInRepository classCheckInRepository;
   private final ClassCheckInValidation classCheckInValidation;
   private final UserValidation userValidation;
   private final ClassSessionValidation classSessionValidation;
   private final ClassGroupUserValidation classGroupUserValidation;
   private final SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor;
   private final ApplicationEventPublisher publisher;

   private static final int POINT = 1;

   @Autowired
   public ClassCheckInService(ClassCheckInRepository classCheckInRepository,
         ClassCheckInValidation classCheckInValidation, UserValidation userValidation,
         ClassSessionValidation classSessionValidation, ClassGroupUserValidation classGroupUserValidation,
         SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor,
         ApplicationEventPublisher publisher) {
      this.classCheckInRepository = classCheckInRepository;
      this.classCheckInValidation = classCheckInValidation;
      this.userValidation = userValidation;
      this.classSessionValidation = classSessionValidation;
      this.classGroupUserValidation = classGroupUserValidation;
      this.sensitiveCheckInDataDecryptor = sensitiveCheckInDataDecryptor;
      this.publisher = publisher;
   }

   @Transactional
   public ClassCheckInResponseDTO createClassCheckIn(ClassCheckInRequestDTO requestDTO) {
      User user = userValidation.validateUserById(requestDTO.getUserId());
      ClassSession classSession = classSessionValidation.validateClassSessionById(requestDTO.getClassSessionId());

      classGroupUserValidation.validateClassGroupUserId(user.getId(), classSession.getClassGroup().getId());

      ClassCheckInId classCheckInId = new ClassCheckInId(user.getId(), classSession.getId());
      classCheckInValidation.validateClassCheckInNotExists(classCheckInId);

      ClassCheckIn classCheckIn = ClassCheckInMapper.toEntity(requestDTO, user, classSession);
      classCheckIn = classCheckInRepository.save(classCheckIn);

      publisher.publishEvent(new CalculatePointsUserEvent(user.getId(), POINT));

      return sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn));
   }

   @Transactional
   public ClassCheckInResponseDTO updateClassCheckIn(ClassCheckInRequestDTO requestDTO) {
      ClassCheckInId classCheckInId = new ClassCheckInId(requestDTO.getUserId(), requestDTO.getClassSessionId());
      ClassCheckIn classCheckIn = classCheckInValidation.validateClassCheckInById(classCheckInId);

      classCheckIn = ClassCheckInMapper.toEntity(requestDTO, classCheckIn.getUser(), classCheckIn.getClassSession(),
            classCheckIn);
      classCheckIn = classCheckInRepository.save(classCheckIn);

      publisher.publishEvent(new CalculatePointsUserEvent(classCheckIn.getUser().getId(), POINT));

      return sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn));
   }

   @Transactional(readOnly = true)
   public List<ClassCheckInResponseDTO> getAllByUserId(UUID userId) {
      List<ClassCheckIn> classCheckIns = classCheckInRepository.findByUserId(userId);
      return classCheckIns.stream()
            .map(classCheckIn -> sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn)))
            .toList();
   }

   @Transactional(readOnly = true)
   public List<ClassCheckInResponseDTO> getAllByClassSessionId(UUID classSessionId) {
      List<ClassCheckIn> classCheckIns = classCheckInRepository.findByClassSessionId(classSessionId);
      return classCheckIns.stream()
            .map(classCheckIn -> sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn)))
            .toList();
   }
}
