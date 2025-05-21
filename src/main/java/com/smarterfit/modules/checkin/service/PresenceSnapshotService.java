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

import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.entity.PresenceSnapshot;
import com.smarterfit.modules.checkin.mapper.PresenceSnapshotMapper;
import com.smarterfit.modules.checkin.repository.PresenceSnapshotRepository;

@Service
public class PresenceSnapshotService {

   private final PresenceSnapshotRepository presenceSnapshotRepository;
   private final ApplicationEventPublisher publisher;

   public PresenceSnapshotService(PresenceSnapshotRepository presenceSnapshotRepository,
                                   ApplicationEventPublisher publisher) {
      this.presenceSnapshotRepository = presenceSnapshotRepository;
        this.publisher = publisher;
   }

   @Transactional
   public PresenceSnapshotResponseDTO registerPresence(Integer quantify) {
      PresenceSnapshot presenceSnapshot = new PresenceSnapshot();
      presenceSnapshot.setPresenceCount(quantify);
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

   /// TODO: Fazer filtragem por data
   @Transactional(readOnly = true)
   public List<PresenceSnapshotResponseDTO> getAll() {
      List<PresenceSnapshot> presenceSnapshots = presenceSnapshotRepository.findAll();
      return presenceSnapshots.stream().map(PresenceSnapshotMapper::toResponse).toList();
   }
}