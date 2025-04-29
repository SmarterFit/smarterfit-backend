package com.smarterfit.modules.traininggroup.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.traininggroup.dto.request.SearchTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.CreateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.UpdateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.entity.TrainingGroup;
import com.smarterfit.modules.traininggroup.event.LastParticipantRemovedEvent;
import com.smarterfit.modules.traininggroup.mapper.TrainingGroupMapper;
import com.smarterfit.modules.traininggroup.repository.TrainingGroupRepository;
import com.smarterfit.modules.traininggroup.repository.TrainingGroupUserRepository;
import com.smarterfit.modules.traininggroup.specification.TrainingGroupSpecifications;
import com.smarterfit.modules.traininggroup.validation.TrainingGroupValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class TrainingGroupService {
   private final TrainingGroupRepository trainingGroupRepository;
   private final TrainingGroupValidation trainingGroupValidation;
   private final UserValidation userValidation;
   private final TrainingGroupUserService trainingGroupUserService;

   @Autowired
   public TrainingGroupService(TrainingGroupRepository trainingGroupRepository,
         TrainingGroupUserRepository trainingGroupUserRepository,
         TrainingGroupValidation trainingGroupValidation, UserValidation userValidation,
         TrainingGroupUserService trainingGroupUserService) {
      this.trainingGroupRepository = trainingGroupRepository;
      this.trainingGroupValidation = trainingGroupValidation;
      this.userValidation = userValidation;
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @Transactional
   public TrainingGroupResponseDTO createTrainingGroup(CreateTrainingGroupRequestDTO requestDTO) {
      trainingGroupValidation.validateFutureDateRange(requestDTO.startDate(), requestDTO.endDate());

      User user = userValidation.validateUserById(requestDTO.ownerId());
      TrainingGroup trainingGroup = TrainingGroupMapper.toEntity(requestDTO, user);
      trainingGroup = trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional(readOnly = true)
   public TrainingGroupResponseDTO getTrainingGroupById(UUID id) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(id);
      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional(readOnly = true)
   public List<TrainingGroupResponseDTO> getAllTrainingGroups() {
      List<TrainingGroup> trainingGroups = trainingGroupRepository.findAll();
      return trainingGroups.stream()
            .map(TrainingGroupMapper::toResponse)
            .toList();
   }

   @Transactional(readOnly = true)
   public Page<TrainingGroupResponseDTO> searchTrainingGroups(
         SearchTrainingGroupRequestDTO requestDTO, Pageable pageable) {
      Specification<TrainingGroup> specification = TrainingGroupSpecifications
            .searchByFilters(requestDTO);

      Page<TrainingGroup> trainingGroups = trainingGroupRepository.findAll(specification, pageable);

      return trainingGroups.map(TrainingGroupMapper::toResponse);
   }

   @Transactional
   public TrainingGroupResponseDTO updateTrainingGroup(UUID id,
         UpdateTrainingGroupRequestDTO requestDTO) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(id);

      trainingGroup = TrainingGroupMapper.toEntity(requestDTO, trainingGroup);
      trainingGroupValidation.validateTrainingGroupDateRange(trainingGroup);

      trainingGroup = trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public void deleteTrainingGroup(UUID id) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(id);
      deleteTrainingGroup(trainingGroup);
   }

   @Transactional
   public void deleteTrainingGroup(TrainingGroup trainingGroup) {
      trainingGroupRepository.delete(trainingGroup);
   }

   @EventListener
   public void handleLastParticipantRemoved(LastParticipantRemovedEvent event) {
      TrainingGroup trainingGroup = event.getTrainingGroup();
      deleteTrainingGroup(trainingGroup);
   }

   @Transactional
   public TrainingGroupResponseDTO activateTrainingGroup(UUID groupId) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(groupId);

      trainingGroupValidation.validateTrainingGroupNotActive(trainingGroup);

      if (!trainingGroupValidation.validateTrainingGroupStarted(trainingGroup)) {
         trainingGroup.setStartDate(LocalDate.now());
      }

      if (trainingGroupValidation.validateTrainingGroupEnded(trainingGroup)) {
         trainingGroup.setEndDate(null);
      }

      trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public TrainingGroupResponseDTO finishTrainingGroup(UUID groupId) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(groupId);

      trainingGroupValidation.validateTrainingGroupActive(trainingGroup);

      trainingGroup.setEndDate(LocalDate.now());
      trainingGroupRepository.save(trainingGroup);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }

   @Transactional
   public TrainingGroupResponseDTO restartTrainingGroup(UUID groupId) {
      TrainingGroup trainingGroup = trainingGroupValidation.validateTrainingGroupById(groupId);

      trainingGroup.setStartDate(LocalDate.now());
      trainingGroup.setEndDate(null);
      trainingGroupRepository.save(trainingGroup);

      trainingGroupUserService.resetPointsByTrainingGroupId(groupId);

      return TrainingGroupMapper.toResponse(trainingGroup);
   }
}
