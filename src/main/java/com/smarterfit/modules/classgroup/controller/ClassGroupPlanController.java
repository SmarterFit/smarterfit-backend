package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classgroupplan.CreateClassGroupPlanDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turma")
public class ClassGroupPlanController {
    public final ClassGroupPlanService classGroupPlanService;

    public ClassGroupPlanController(ClassGroupPlanService classGroupPlanService) {
        this.classGroupPlanService = classGroupPlanService;
    }


    @RequireRole(RoleType.ADMIN)
    @PostMapping("/planos/cadastrar")
    public ResponseEntity<Void> addPlanToClassGroup(@RequestBody @Valid CreateClassGroupPlanDTO  requestDTO) {
        classGroupPlanService.addPlanToClassGroup(requestDTO);
        return ResponseEntity.ok().build();
    }

    @RequireRole(RoleType.ADMIN)
    @DeleteMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> removePlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupPlanService.removePlanToClassGroup(planId, classGroupId);
        return ResponseEntity.noContent().build();
    }
}
