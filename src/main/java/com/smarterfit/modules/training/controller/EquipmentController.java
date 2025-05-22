package com.smarterfit.modules.training.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.training.dto.request.EquipmentRequestDTO;
import com.smarterfit.modules.training.dto.response.EquipmentResponseDTO;
import com.smarterfit.modules.training.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipamentos")
public class EquipmentController {
    public final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }


    @RequireRole(RoleType.ADMIN)
    @PostMapping("/cadastrar")
    public ResponseEntity<EquipmentResponseDTO> createEquipment(@RequestBody @Valid EquipmentRequestDTO requestDTO) {

        EquipmentResponseDTO responseDTO = equipmentService.createEquipment(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @GetMapping("/buscar/{name}")
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipmentByName(@PathVariable String name) {
        return ResponseEntity.ok(equipmentService.getAllEquipmentByName(name));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @RequireRole(RoleType.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> updateEquipmentById(
            @PathVariable UUID id,
            @RequestBody @Valid EquipmentRequestDTO requestDTO) {
        return ResponseEntity.ok(equipmentService.updateEquipmentById(id, requestDTO));
    }

    @RequireRole(RoleType.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentById(@PathVariable UUID id) {
        equipmentService.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
    }
}
