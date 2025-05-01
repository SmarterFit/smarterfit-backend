/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.checkin.entity.PresenceSnapshot;
import com.smarterfit.modules.checkin.service.PresenceSnapshotService;

import lombok.RequiredArgsConstructor;

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