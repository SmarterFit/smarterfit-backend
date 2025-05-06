package com.smarterfit.modules.classgroup.controller;

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


    @PostMapping("/planos/cadastrar")
    public ResponseEntity<Void> addPlanToClassGroup(@RequestBody @Valid CreateClassGroupPlanDTO  requestDTO,
                                                    @RequestHeader("X-User-Id") UUID requesterId) {
        classGroupPlanService.addPlanToClassGroup(requestDTO, requesterId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> removePlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId,
                                                       @RequestHeader("X-User-Id") UUID requesterId) {
        classGroupPlanService.removePlanToClassGroup(planId, classGroupId, requesterId);
        return ResponseEntity.noContent().build();
    }
}
