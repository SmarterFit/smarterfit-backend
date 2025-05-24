package com.smarterfit.modules.ai.generator.util;


import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;

public class WorkoutPlanPromptBuilder {

    public String buildInputJson(TrainingGoalResponseDTO goalDto) {
        return String.format("""
            {
              "goal": "%s",
              "experienceLevel": "%s",
              "weeklyFrequency": %d
            }
            """,
                goalDto.getGoal(),
                goalDto.getExperienceLevel(),
                goalDto.getWeeklyFrequency()
        );
    }
}
