package com.smarterfit.modules.ai.generator.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarterfit.common.exceptions.GenerateAIException;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanUpdateRequestDTO;

import java.util.UUID;

public class WorkoutPlanAIResponseParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WorkoutPlanRequestDTO parse(String aiResponse, UUID trainingGoalId) {
        try {
            JsonNode root = objectMapper.readTree(aiResponse);
            String title       = root.get("title").asText();
            String description = root.get("description").asText();

            WorkoutPlanRequestDTO dto = new WorkoutPlanRequestDTO();
            dto.setTrainingGoalId(trainingGoalId);
            dto.setTitle(title);
            dto.setDescription(description);
            return dto;
        } catch (Exception e) {
            throw new GenerateAIException("Failed to parse AI response JSON");
        }
    }

    public WorkoutPlanUpdateRequestDTO parse(String aiResponse) {
        try {
            JsonNode root = objectMapper.readTree(aiResponse);
            String title       = root.get("title").asText();
            String description = root.get("description").asText();

            WorkoutPlanUpdateRequestDTO dto = new WorkoutPlanUpdateRequestDTO();
            dto.setTitle(title);
            dto.setDescription(description);
            return dto;
        } catch (Exception e) {
            throw new GenerateAIException("Failed to parse AI response JSON");
        }
    }
}