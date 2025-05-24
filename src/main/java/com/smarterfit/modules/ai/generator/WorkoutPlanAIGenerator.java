package com.smarterfit.modules.ai.generator;

import com.smarterfit.modules.ai.generator.util.WorkoutPlanAIResponseParser;
import com.smarterfit.modules.ai.generator.util.WorkoutPlanPromptBuilder;
import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.service.TrainingGoalService;
import com.smarterfit.modules.training.service.WorkoutPlanService;
import com.smarterfit.modules.training.validation.WorkoutPlanValidation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkoutPlanAIGenerator {

    @Autowired
    private @Qualifier("trainingChatClient") ChatClient chatClient;

    private final TrainingGoalService trainingGoalService;
    private final WorkoutPlanService workoutPlanService;
    private final WorkoutPlanValidation workoutPlanValidation;
    private final WorkoutPlanPromptBuilder promptBuilder = new WorkoutPlanPromptBuilder();
    private final WorkoutPlanAIResponseParser responseParser = new WorkoutPlanAIResponseParser();


    @Autowired
    public WorkoutPlanAIGenerator(
            TrainingGoalService trainingGoalService,
            WorkoutPlanService workoutPlanService,
            WorkoutPlanValidation workoutPlanValidation) {
        this.trainingGoalService = trainingGoalService;
        this.workoutPlanService = workoutPlanService;
        this.workoutPlanValidation = workoutPlanValidation;
    }


    public WorkoutPlanResponseDTO generatePlan(UUID requesterId) {
        TrainingGoalResponseDTO goalDto = trainingGoalService.getTrainingGoalByUserId(requesterId);

        String inputJson = promptBuilder.buildInputJson(goalDto);

        String aiResponse = chatClient.prompt()
                .user(inputJson)
                .call()
                .content();

        System.out.println("AI Response: " + aiResponse);
        if(workoutPlanValidation.noExistsWorkoutPlanById(requesterId)) {
            var requestDto = responseParser.parse(aiResponse, requesterId);
            return workoutPlanService.createWorkoutPlan(requestDto);
        }
        var requestDto = responseParser.parse(aiResponse);
        return workoutPlanService.updateWorkoutPlan(requesterId, requestDto);

    }
}
