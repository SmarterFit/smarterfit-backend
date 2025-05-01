/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.entity.CheckIn;
import com.smarterfit.modules.checkin.repository.CheckInRepository;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.UserRepository;

/// TODO: Retornar dtos de response no lugar de entidades

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PresenceSnapshotService presenceSnapshotService;

    @Transactional
    public CheckIn doCheckIn(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

         /// TODO: Mover para um validation
        Optional<CheckIn> existingCheckIn = checkInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId);
        if (existingCheckIn.isPresent()) {
            throw new IllegalStateException("Já existe um check-in aberto para este usuário.");
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setUser(user);
        
        presenceSnapshotService.registerPresence(userId);
        
        return checkInRepository.save(checkIn);
    }

    @Transactional
    public CheckIn doCheckOut(UUID userId) {
        Optional<CheckIn> checkInOptional = checkInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId);

        if (checkInOptional.isEmpty()) {
            throw new IllegalStateException("Não há check-in aberto para este usuário.");
        }

        CheckIn checkIn = checkInOptional.get();
        checkIn.setCheckOutTime(java.time.LocalDateTime.now());
        return checkInRepository.save(checkIn);
    }
}