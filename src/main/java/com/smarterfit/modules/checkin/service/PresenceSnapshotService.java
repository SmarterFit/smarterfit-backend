/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.util.List;

import com.smarterfit.modules.checkin.event.AllCheckOutEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.dto.request.FilterPresenceSnapshotRequestDTO;
import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.entity.PresenceSnapshot;
import com.smarterfit.modules.checkin.mapper.PresenceSnapshotMapper;
import com.smarterfit.modules.checkin.repository.GymCheckInRepository;
import com.smarterfit.modules.checkin.repository.PresenceSnapshotRepository;

@Service
public class PresenceSnapshotService {

   private final PresenceSnapshotRepository presenceSnapshotRepository;
   private final GymCheckInRepository gymCheckInRepository;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public PresenceSnapshotService(PresenceSnapshotRepository presenceSnapshotRepository,
         GymCheckInRepository gymCheckInRepository,
         ApplicationEventPublisher publisher) {
      this.presenceSnapshotRepository = presenceSnapshotRepository;
      this.gymCheckInRepository = gymCheckInRepository;
      this.publisher = publisher;
   }

   @Transactional
   public PresenceSnapshotResponseDTO registerPresence() {
      Integer presenceCount = gymCheckInRepository.countByCheckOutTimeIsNull();

      PresenceSnapshot presenceSnapshot = new PresenceSnapshot();
      presenceSnapshot.setPresenceCount(presenceCount);
      presenceSnapshot = presenceSnapshotRepository.save(presenceSnapshot);

      return PresenceSnapshotMapper.toResponse(presenceSnapshot);
   }

   @Transactional
   public void resetPresence() {
      PresenceSnapshot presenceSnapshot = new PresenceSnapshot();
      presenceSnapshot.setPresenceCount(0);
      presenceSnapshotRepository.save(presenceSnapshot);

      publisher.publishEvent(new AllCheckOutEvent(this));
   }

   @Transactional(readOnly = true)
   public List<PresenceSnapshotResponseDTO> getAll() {
      List<PresenceSnapshot> presenceSnapshots = presenceSnapshotRepository.findAll();
      return presenceSnapshots.stream().map(PresenceSnapshotMapper::toResponse).toList();
   }

   @Transactional(readOnly = true)
   public PresenceSnapshotResponseDTO getLast() {
      PresenceSnapshot presenceSnapshot = presenceSnapshotRepository.findTopByOrderByCreatedAtDesc();
      return PresenceSnapshotMapper.toResponse(presenceSnapshot);
   }

   @Transactional(readOnly = true)
   public List<PresenceSnapshotResponseDTO> filterByDate(FilterPresenceSnapshotRequestDTO requestDTO) {
      List<PresenceSnapshot> presenceSnapshots = presenceSnapshotRepository
            .findByCreatedAtBetweenOrderByCreatedAt(requestDTO.getStartDate(), requestDTO.getEndDate());
      return presenceSnapshots.stream().map(PresenceSnapshotMapper::toResponse).toList();
   }
}