package com.smarterfit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.training_group.SearchDTO;
import com.smarterfit.dto.request.training_group.TrainingGroupDTO;
import com.smarterfit.dto.request.training_group.UpdateDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupResponseDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupUserResponseDTO;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.model.TrainingGroup.TrainingGroup;
import com.smarterfit.model.TrainingGroup.TrainingGroupUser;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.TrainingGroupRepository;
import com.smarterfit.repository.TrainingGroupUserRepository;
import com.smarterfit.specification.TrainingGroupSpecifications;
import com.smarterfit.util.mapper.TrainingGroupMapper;
import com.smarterfit.util.validation.TrainingGroupValidation;
import com.smarterfit.util.validation.UserValidation;

@Service
public class TrainingGroupService {
   private final TrainingGroupRepository trainingGroupRepository;
   private final TrainingGroupUserRepository trainingGroupUserRepository;
   private final TrainingGroupValidation trainingGroupValidation;
   private final UserValidation userValidation;

   @Autowired
   public TrainingGroupService(TrainingGroupRepository trainingGroupRepository,
         TrainingGroupUserRepository trainingGroupUserRepository,
         TrainingGroupValidation trainingGroupValidation, UserValidation userValidation) {
      this.trainingGroupRepository = trainingGroupRepository;
      this.trainingGroupUserRepository = trainingGroupUserRepository;
      this.trainingGroupValidation = trainingGroupValidation;
      this.userValidation = userValidation;
   }

   @Transactional
   public TrainingGroupResponseDTO createTrainingGroup(TrainingGroupDTO trainingGroupDTO) {
      User user = userValidation.validateUserById(trainingGroupDTO.ownerId());

      TrainingGroup trainingGroup = TrainingGroupMapper.toEntity(user, trainingGroupDTO);

      trainingGroup = trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional(readOnly = true)
   public TrainingGroupResponseDTO getTrainingGroupById(UUID id) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(id);
      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupResponseDTO> getAllTrainingGroups() {
      List<TrainingGroup> trainingGroups = trainingGroupRepository.findAll();
      return trainingGroups.stream()
            .map(TrainingGroupMapper::toResponse)
            .toList();
   }

   @Transactional
   public TrainingGroupResponseDTO updateTrainingGroup(UUID id, UpdateDTO updateDTO) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(id);

      trainingGroup = TrainingGroupMapper.toEntity(trainingGroup, updateDTO);
      trainingGroup = trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public void deleteTrainingGroup(UUID id) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(id);
      trainingGroupRepository.delete(trainingGroup);
   }

   @Transactional(readOnly = true)
   public Page<TrainingGroupResponseDTO> searchTrainingGroups(SearchDTO searchDTO, Pageable pageable) {
      Specification<TrainingGroup> specification = TrainingGroupSpecifications.searchByFilters(searchDTO);

      Page<TrainingGroup> trainingGroups = trainingGroupRepository.findAll(specification, pageable);

      return trainingGroups.map(TrainingGroupMapper::toResponse);
   }

   @Transactional
   public TrainingGroupUserResponseDTO addUserToTrainingGroup(UUID groupId, UUID userId) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(groupId);

      User user = userValidation.validateUserById(userId);

      if (trainingGroup.getParticipants().stream()
            .anyMatch(trainingGroupUser -> trainingGroupUser.getUser().getId().equals(user.getId()))) {
         throw new BusinessException("User already in the group");
      }

      TrainingGroupUser trainingGroupUser = new TrainingGroupUser();
      trainingGroupUser.setUser(user);
      trainingGroupUser.setTrainingGroup(trainingGroup);

      trainingGroupUser = trainingGroupUserRepository.save(trainingGroupUser);

      return TrainingGroupMapper.toResponse(trainingGroupUser);
   }

   @Transactional
   public void removeUserFromTrainingGroup(UUID groupId, UUID userId) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(groupId);
      TrainingGroupUser trainingGroupUser = trainingGroupUserRepository
            .findByTrainingGroupIdAndUserId(groupId, userId);

      trainingGroup.getParticipants().remove(trainingGroupUser);

      if (trainingGroup.getParticipants().isEmpty()) {
         trainingGroupRepository.delete(trainingGroup);
      } else {
         trainingGroupValidation.validateAtLeastOneAdmin(trainingGroup.getParticipants());
         trainingGroupRepository.save(trainingGroup);
      }
   }

   @Transactional(readOnly = true)
   public Page<TrainingGroupUserResponseDTO> getUsersInTrainingGroup(UUID groupId, Pageable pageable) {
      Page<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository
            .findByTrainingGroupId(groupId, pageable);

      return trainingGroupUsers.map(TrainingGroupMapper::toResponse);
   }

   @Transactional
   public TrainingGroupResponseDTO finishTrainingGroup(UUID groupId) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(groupId);

      trainingGroupValidation.validateTrainingGroupActive(trainingGroup);

      trainingGroup.setEndDate(LocalDate.now());
      trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public TrainingGroupResponseDTO activateTrainingGroup(UUID groupId) {
      TrainingGroup trainingGroup = trainingGroupValidation.findTrainingGroupById(groupId);

      trainingGroupValidation.validateTrainingGroupNotActive(trainingGroup);

      trainingGroup.setStartDate(LocalDate.now());
      trainingGroup.setEndDate(null);

      trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public TrainingGroupUserResponseDTO setUserAsAdmin(UUID groupId, UUID userId) {
      trainingGroupValidation.findTrainingGroupById(groupId);

      TrainingGroupUser trainingGroupUser = trainingGroupUserRepository
            .findByTrainingGroupIdAndUserId(groupId, userId);

      if (trainingGroupUser == null) {
         throw new BusinessException("User not found in the group");
      }

      trainingGroupUser.setIsAdmin(true);
      trainingGroupUserRepository.save(trainingGroupUser);

      return TrainingGroupMapper.toResponse(trainingGroupUser);
   }
}
