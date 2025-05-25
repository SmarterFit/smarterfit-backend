package com.smarterfit.modules.traininggroup.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;
import com.smarterfit.modules.traininggroup.entity.TrainingGroup;
import com.smarterfit.modules.traininggroup.entity.TrainingGroupUser;
import com.smarterfit.modules.traininggroup.entity.id.TrainingGroupUserId;
import com.smarterfit.modules.traininggroup.event.LastParticipantRemovedEvent;
import com.smarterfit.modules.traininggroup.mapper.TrainingGroupMapper;
import com.smarterfit.modules.traininggroup.mapper.TrainingGroupUserMapper;
import com.smarterfit.modules.traininggroup.repository.TrainingGroupUserRepository;
import com.smarterfit.modules.traininggroup.util.SensitiveTrainingGroupDataDecryptor;
import com.smarterfit.modules.traininggroup.validation.TrainingGroupUserValidation;
import com.smarterfit.modules.traininggroup.validation.TrainingGroupValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class TrainingGroupUserService {
   private final TrainingGroupUserRepository trainingGroupUserRepository;
   private final TrainingGroupValidation trainingGroupValidation;
   private final UserValidation userValidation;
   private final TrainingGroupUserValidation trainingGroupUserValidation;
   private final SensitiveTrainingGroupDataDecryptor sensitiveTrainingGroupDataDecryptor;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public TrainingGroupUserService(TrainingGroupUserRepository trainingGroupUserRepository,
         TrainingGroupValidation trainingGroupValidation, UserValidation userValidation,
         TrainingGroupUserValidation trainingGroupUserValidation,
         SensitiveTrainingGroupDataDecryptor sensitiveTrainingGroupDataDecryptor,
         ApplicationEventPublisher publisher) {
      this.trainingGroupUserRepository = trainingGroupUserRepository;
      this.trainingGroupValidation = trainingGroupValidation;
      this.userValidation = userValidation;
      this.trainingGroupUserValidation = trainingGroupUserValidation;
      this.sensitiveTrainingGroupDataDecryptor = sensitiveTrainingGroupDataDecryptor;
      this.publisher = publisher;
   }

   @Transactional
   public TrainingGroupUserResponseDTO addUserToTrainingGroup(UUID groupId, UUID userId) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(groupId);
      User user = userValidation.validateUserById(userId);

      trainingGroupUserValidation.validateUserNotInTrainingGroup(trainingGroup, user);

      TrainingGroupUser trainingGroupUser = new TrainingGroupUser();
      trainingGroupUser.setUser(user);
      trainingGroupUser.setTrainingGroup(trainingGroup);

      trainingGroupUser = trainingGroupUserRepository.save(trainingGroupUser);

      return sensitiveTrainingGroupDataDecryptor.decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser));
   }

   @Transactional
   public void removeUserFromTrainingGroup(UUID groupId, UUID userId) {
      TrainingGroupUser trainingGroupUser = trainingGroupUserValidation
            .validateTrainingGroupUserById(new TrainingGroupUserId(groupId, userId));

      TrainingGroup trainingGroup = trainingGroupUser.getTrainingGroup();
      trainingGroup.getParticipants().remove(trainingGroupUser);

      if (trainingGroup.getParticipants().isEmpty()) {
         publisher.publishEvent(new LastParticipantRemovedEvent(trainingGroup));
      } else {
         trainingGroupUserValidation.validateAtLeastOneAdmin(trainingGroup);
      }
   }

   @Transactional(readOnly = true)
   public TrainingGroupUserResponseDTO getTrainingGroupUser(UUID groupId, UUID userId) {
      TrainingGroupUser trainingGroupUser = trainingGroupUserValidation
            .validateTrainingGroupUserById(new TrainingGroupUserId(groupId, userId));

      return TrainingGroupUserMapper.toResponse(trainingGroupUser);
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupUserResponseDTO> getAllTrainingGroupUser() {
      return trainingGroupUserRepository.findAll().stream()
            .map(trainingGroupUser -> sensitiveTrainingGroupDataDecryptor
                  .decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser)))
            .toList();
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupUserResponseDTO> getAllUsersByTrainingGroupId(UUID groupId) {
      List<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository
            .findByTrainingGroupId(groupId);

      return trainingGroupUsers.stream().map(trainingGroupUser -> sensitiveTrainingGroupDataDecryptor
            .decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser))).toList();
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupResponseDTO> getAllTrainingGroupsByUserId(UUID userId) {
      List<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository
            .findByUserId(userId);

      return trainingGroupUsers.stream()
            .map(trainingGroupUser -> TrainingGroupMapper.toResponse(trainingGroupUser.getTrainingGroup()))
            .toList();
   }

   @Transactional
   public TrainingGroupUserResponseDTO setUserAsAdmin(UUID groupId, UUID userId) {
      TrainingGroupUser trainingGroupUser = trainingGroupUserValidation
            .validateTrainingGroupUserById(new TrainingGroupUserId(groupId, userId));

      trainingGroupUserValidation.validateTrainingGroupUserByIsAdmin(trainingGroupUser, false);

      trainingGroupUser.setIsAdmin(true);
      trainingGroupUserRepository.save(trainingGroupUser);

      return sensitiveTrainingGroupDataDecryptor
            .decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser));
   }

   @Transactional
   public TrainingGroupUserResponseDTO removeUserAsAdmin(UUID groupId, UUID userId) {
      TrainingGroupUser trainingGroupUser = trainingGroupUserValidation
            .validateTrainingGroupUserById(new TrainingGroupUserId(groupId, userId));

      trainingGroupUserValidation.validateTrainingGroupUserByIsAdmin(trainingGroupUser, true);

      trainingGroupUser.setIsAdmin(false);
      trainingGroupUserRepository.save(trainingGroupUser);

      return sensitiveTrainingGroupDataDecryptor
            .decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser));
   }

   @Transactional
   public void resetPointsByTrainingGroupId(UUID trainingGroupId) {
      trainingGroupUserRepository.resetPointsByTrainingGroupId(trainingGroupId);
   }

   @Transactional
   public void updatePoints(UUID userId, Integer addPoints) {
      List<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository
            .findByUserId(userId);

      trainingGroupUsers.stream().map(trainingGroupUser1 -> {
         trainingGroupUser1.setPoints(trainingGroupUser1.getPoints() + addPoints);
         return trainingGroupUser1;
      }).toList();

      trainingGroupUserRepository.saveAll(trainingGroupUsers);
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupUserResponseDTO> getRankByTrainingGroupId(UUID trainingGroupId) {
      List<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository
            .findByTrainingGroupIdOrderByPointsDesc(trainingGroupId);

      return trainingGroupUsers.stream()
            .map(trainingGroupUser -> sensitiveTrainingGroupDataDecryptor
                  .decrypt(TrainingGroupUserMapper.toResponse(trainingGroupUser)))
            .toList();
   }
}
