package com.smarterfit.controller;

import com.smarterfit.model.PresenceSnapshot;
import com.smarterfit.service.PresenceSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/presence-snapshots")
@RequiredArgsConstructor
public class PresenceSnapshotController {

    private final PresenceSnapshotService presenceSnapshotService;

    @PostMapping("/register")
    public ResponseEntity<PresenceSnapshot> registerPresence(@RequestParam UUID userId) {
        PresenceSnapshot presenceSnapshot = presenceSnapshotService.registerPresence(userId);
        return ResponseEntity.ok(presenceSnapshot);
    }
}
