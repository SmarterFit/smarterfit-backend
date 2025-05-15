package com.smarterfit.modules.ai.tools.classes;

import com.smarterfit.modules.billing.dto.response.plan.PlanResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupPlanService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClassPlansTools {

    private final ClassGroupPlanService classGroupPlanService;

    public ClassPlansTools(ClassGroupPlanService classGroupPlanService) {
        this.classGroupPlanService = classGroupPlanService;
    }

    @Tool(description = "Buscar planos associados a uma turma da academia")
    public List<PlanResponseDTO> getPlansForClassGroup(
            @ToolParam(description = "ID da turma (class group)") UUID classGroupId
    ) {
        return classGroupPlanService.getPlansToClassGroup(classGroupId);
    }
}