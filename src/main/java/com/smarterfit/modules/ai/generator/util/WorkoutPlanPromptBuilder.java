package com.smarterfit.modules.ai.generator.util;


import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;

import java.time.format.DateTimeFormatter;

public class WorkoutPlanPromptBuilder {

    public String buildInputJson(TrainingGoalResponseDTO goalDto, Profile profile) {
        String gender = profile != null && profile.getGender() != null
                ? profile.getGender().toString()
                : "OUTRO"; // default

        String birthDate = profile != null && profile.getBirthDate() != null
                ? profile.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                : "2000-01-01"; // default

        return String.format("""
        {
          "goal": "%s",
          "experienceLevel": "%s",
          "weeklyFrequency": %d,
          "gender": "%s",
          "birthDate": "%s"
        }
        """,
                goalDto.getGoal(),
                goalDto.getExperienceLevel(),
                goalDto.getWeeklyFrequency(),
                gender,
                birthDate
        );
    }

}