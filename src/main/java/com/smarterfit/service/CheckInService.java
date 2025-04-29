package com.smarterfit.service;

import com.smarterfit.model.CheckIn;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.CheckInRepository;
import com.smarterfit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smarterfit.service.PresenceSnapshotService;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

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

        Optional<CheckIn> existingCheckIn = checkInRepository.findFirstByUserIdAndCheckoutTimeIsNullOrderByCheckinTimeDesc(userId);
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
        Optional<CheckIn> checkInOptional = checkInRepository.findFirstByUserIdAndCheckoutTimeIsNullOrderByCheckinTimeDesc(userId);

        if (checkInOptional.isEmpty()) {
            throw new IllegalStateException("Não há check-in aberto para este usuário.");
        }

        CheckIn checkIn = checkInOptional.get();
        checkIn.setCheckoutTime(java.time.LocalDateTime.now());
        return checkInRepository.save(checkIn);
    }
}
