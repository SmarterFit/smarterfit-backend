package com.smarterfit.modules.employee.controller;

import com.smarterfit.modules.employee.dto.request.schedule.EmployeeSchedulerRequestDTO;
import com.smarterfit.modules.employee.dto.response.EmployeeScheduleResponseDTO;
import com.smarterfit.modules.employee.sevice.EmployeeSchedulerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionarios/horarios")
public class EmployeeSchedulerController {


    private final EmployeeSchedulerService employeeSchedulerService;

    public EmployeeSchedulerController(EmployeeSchedulerService employeeSchedulerService) {
        this.employeeSchedulerService = employeeSchedulerService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EmployeeScheduleResponseDTO> create(@RequestBody @Valid EmployeeSchedulerRequestDTO requestDTO,
                                                              @RequestHeader("X-User-Id") UUID requesterId) {
        EmployeeScheduleResponseDTO response = employeeSchedulerService.createEmployeeSchedule(requestDTO, requesterId);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EmployeeScheduleResponseDTO>> getByUserId(@PathVariable UUID userId) {
        List<EmployeeScheduleResponseDTO> schedules = employeeSchedulerService.getAllEmployeeScheduleByUserId(userId);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDTO> update(@PathVariable UUID id,
                                                              @RequestBody @Valid EmployeeSchedulerRequestDTO requestDTO,
                                                              @RequestHeader("X-User-Id") UUID requesterId) {
        EmployeeScheduleResponseDTO updated = employeeSchedulerService.updateEmployeeScheduleById(id, requestDTO, requesterId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id,
                                       @RequestHeader("X-User-Id") UUID requesterId) {
        employeeSchedulerService.deleteEmployeeScheduleById(id, requesterId);
        return ResponseEntity.noContent().build();
    }
}
