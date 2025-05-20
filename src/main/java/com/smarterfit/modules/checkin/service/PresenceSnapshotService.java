/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.entity.PresenceSnapshot;
import com.smarterfit.modules.checkin.mapper.PresenceSnapshotMapper;
import com.smarterfit.modules.checkin.repository.PresenceSnapshotRepository;

@Service
public class PresenceSnapshotService {

   private final PresenceSnapshotRepository presenceSnapshotRepository;

   @Autowired
   public PresenceSnapshotService(PresenceSnapshotRepository presenceSnapshotRepository) {
      this.presenceSnapshotRepository = presenceSnapshotRepository;
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

      /// TODO: Lançar evento de dar check-out em todos os gym-check-in abertos
   }

   /// TODO: Fazer filtragem por data
   @Transactional(readOnly = true)
   public List<PresenceSnapshotResponseDTO> getAll() {
      List<PresenceSnapshot> presenceSnapshots = presenceSnapshotRepository.findAll();
      return presenceSnapshots.stream().map(PresenceSnapshotMapper::toResponse).toList();
   }
}