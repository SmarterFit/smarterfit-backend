package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classgroup.CreateClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupPlanService;
import com.smarterfit.modules.classgroup.service.ClassGroupService;
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


    @PostMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> addPlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupPlanService.addPlanToClassGroup(planId, classGroupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> removePlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupPlanService.removePlanToClassGroup(planId, classGroupId);
        return ResponseEntity.noContent().build();
    }
}
