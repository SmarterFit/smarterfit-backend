package com.smarterfit.modules.ai.tools.training;


import com.smarterfit.modules.ai.generator.WorkoutPlanAIGenerator;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkoutPlanTool {

    private final WorkoutPlanAIGenerator aiGenerator;

    public WorkoutPlanTool(@Lazy WorkoutPlanAIGenerator aiGenerator) {
        this.aiGenerator = aiGenerator;
    }

    @Tool(description = "Gerar um plano de treino personalizado para o usuário.")
    public String generateWorkoutPlan(
            @ToolParam(description = "User ID (UUID)") UUID userId) {

        var plan = aiGenerator.generatePlan(userId);
        return String.format("🏋️  Plano de exercícios gerado:\n\n*%s*\n\n%s", plan.getTitle(), plan.getDescription());
    }
}
