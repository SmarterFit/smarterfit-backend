package com.smarterfit.modules.employee.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
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

    @RequireRole(RoleType.ADMIN)
    @PostMapping("/cadastrar")
    public ResponseEntity<EmployeeScheduleResponseDTO> create(@RequestBody @Valid EmployeeSchedulerRequestDTO requestDTO) {
        EmployeeScheduleResponseDTO response = employeeSchedulerService.createEmployeeSchedule(requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EmployeeScheduleResponseDTO>> getByUserId(@PathVariable UUID userId) {
        List<EmployeeScheduleResponseDTO> schedules = employeeSchedulerService.getAllEmployeeScheduleByUserId(userId);
        return ResponseEntity.ok(schedules);
    }

    @RequireRole(RoleType.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDTO> update(@PathVariable UUID id,
                                                              @RequestBody @Valid EmployeeSchedulerRequestDTO requestDTO) {
        EmployeeScheduleResponseDTO updated = employeeSchedulerService.updateEmployeeScheduleById(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @RequireRole(RoleType.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeSchedulerService.deleteEmployeeScheduleById(id);
        return ResponseEntity.noContent().build();
    }
}
