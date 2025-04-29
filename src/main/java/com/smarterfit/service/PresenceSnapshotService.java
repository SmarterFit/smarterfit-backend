package com.smarterfit.service;

import com.smarterfit.model.PresenceSnapshot;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.PresenceSnapshotRepository;
import com.smarterfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresenceSnapshotService {

    private final PresenceSnapshotRepository resenceSnapshotRepository;
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
