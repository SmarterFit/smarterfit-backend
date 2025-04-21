package com.smarterfit.controller;

import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.service.ModalityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/modalidade")
@CrossOrigin
public class ModalityController {

    public final ModalityService modalityService;

    public ModalityController(ModalityService modalityService) {
        this.modalityService = modalityService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ModalityResponseDTO> createModality(@RequestBody @Valid ModalityRequestDTO modalityRequest) {
        ModalityResponseDTO responseDTO = modalityService.createModality(modalityRequest);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalityResponseDTO> getModalityById(@PathVariable UUID id) {
        return ResponseEntity.ok(modalityService.getModalityById(id));
    }

    @GetMapping("/buscar/{name}")
    public ResponseEntity<List<ModalityResponseDTO>> getAllModalityByName(@PathVariable String name) {
        return ResponseEntity.ok(modalityService.getAllModalityByName(name));
    }
    @GetMapping
    public ResponseEntity<List<ModalityResponseDTO>> getAllModality() {
        return ResponseEntity.ok(modalityService.getAllModality());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalityResponseDTO> updateModalityById(
            @PathVariable UUID id,
            @RequestBody @Valid ModalityRequestDTO modalityRequest) {
        return ResponseEntity.ok(modalityService.updateModalityById(id, modalityRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModalityById(@PathVariable UUID id) {
        modalityService.deleteModalityById(id);
        return ResponseEntity.noContent().build();
    }

}
