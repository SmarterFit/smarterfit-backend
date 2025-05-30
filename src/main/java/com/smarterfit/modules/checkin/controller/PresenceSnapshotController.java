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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.checkin.dto.request.FilterPresenceSnapshotRequestDTO;
import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.service.PresenceSnapshotService;

import jakarta.validation.Valid;

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

    @GetMapping("/latest")
    public ResponseEntity<PresenceSnapshotResponseDTO> getLast() {
        return ResponseEntity.ok(presenceSnapshotService.getLast());
    }

    @PostMapping("/date-range")
    public ResponseEntity<List<PresenceSnapshotResponseDTO>> filterByDate(
            @RequestBody @Valid FilterPresenceSnapshotRequestDTO request) {
        return ResponseEntity.ok(presenceSnapshotService.filterByDate(request));
    }
}