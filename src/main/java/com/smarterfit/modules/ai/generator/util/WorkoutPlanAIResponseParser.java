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
            String cleanJson = sanitizeJson(aiResponse);

            JsonNode root = objectMapper.readTree(cleanJson);
            WorkoutPlanRequestDTO dto = new WorkoutPlanRequestDTO();
            dto.setTrainingGoalId(trainingGoalId);

            if (root.has("title")) {
                dto.setTitle(root.get("title").asText());
            }

            if (root.has("description")) {
                dto.setDescription(root.get("description").asText());
            }

            return dto;
        } catch (Exception e) {
            throw new GenerateAIException("AI response processing failed: " + e.getMessage());
        }
    }

    public WorkoutPlanUpdateRequestDTO parse(String aiResponse) {
        try {
            String cleanJson = sanitizeJson(aiResponse);

            JsonNode root = objectMapper.readTree(cleanJson);
            WorkoutPlanUpdateRequestDTO dto = new WorkoutPlanUpdateRequestDTO();

            if (root.has("title")) {
                dto.setTitle(root.get("title").asText());
            }

            if (root.has("description")) {
                dto.setDescription(root.get("description").asText());
            }

            return dto;
        } catch (Exception e) {
            throw new GenerateAIException("AI response processing failed: " + e.getMessage());
        }
    }

    private String sanitizeJson(String raw) {
        String cleaned = raw.replaceAll("(?s)^```(json)?\\s*", "")
                .replaceAll("\\s*```$", "")
                .trim();
        cleaned = cleaned.replace("\r", "");

        cleaned = cleaned.replace("\\\\n", "\\n");

        cleaned = cleaned.replace("\\\"", "\"");

        return cleaned;
    }


}
