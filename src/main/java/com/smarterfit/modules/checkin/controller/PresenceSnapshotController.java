/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.service.PresenceSnapshotService;

@RestController
@RequestMapping("/presence-snapshots")
@CrossOrigin
public class PresenceSnapshotController {

    private final PresenceSnapshotService presenceSnapshotService;

    @Autowired
    public PresenceSnapshotController(PresenceSnapshotService presenceSnapshotService) {
        this.presenceSnapshotService = presenceSnapshotService;
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPresence() {
        presenceSnapshotService.resetPresence();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PresenceSnapshotResponseDTO>> getAll() {
        return ResponseEntity.ok(presenceSnapshotService.getAll());
    }
}