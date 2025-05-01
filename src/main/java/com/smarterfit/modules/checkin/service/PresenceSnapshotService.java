/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smarterfit.modules.checkin.entity.PresenceSnapshot;
import com.smarterfit.modules.checkin.repository.PresenceSnapshotRepository;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/// TODO: Visualizar quantidade de alunos presentes na hora
/// TODO: Registro a cada 5 minutos (apenas em horário comercial) da quantidade presente
/// TODO: Funções de retorno de todas as quantidades ou em um range de tempo (apenas para funcionários)
/// TODO: Possibilidade de alteração de quantidade de alunos presentes na academia
@Service
@RequiredArgsConstructor
public class PresenceSnapshotService {

   private final PresenceSnapshotRepository presenceSnapshotRepository;
   private final UserRepository userRepository;

   public PresenceSnapshot registerPresence(UUID userId) {
      User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

      PresenceSnapshot presenceSnapshot = new PresenceSnapshot();
      presenceSnapshot.setUser(user);
      presenceSnapshot.setPresenceTime(LocalDateTime.now());

      return presenceSnapshotRepository.save(presenceSnapshot);
   }
}