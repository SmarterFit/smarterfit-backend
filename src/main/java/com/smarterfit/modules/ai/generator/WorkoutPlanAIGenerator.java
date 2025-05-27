package com.smarterfit.modules.ai.generator;

import com.smarterfit.common.util.MarkdownUtils;
import com.smarterfit.modules.ai.generator.util.WorkoutPlanAIResponseParser;
import com.smarterfit.modules.ai.generator.util.WorkoutPlanPromptBuilder;
import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.service.TrainingGoalService;
import com.smarterfit.modules.training.service.WorkoutPlanService;
import com.smarterfit.modules.training.validation.WorkoutPlanValidation;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkoutPlanAIGenerator {

    private final ChatClient chatClient;
    private final TrainingGoalService trainingGoalService;
    private final WorkoutPlanService workoutPlanService;
    private final WorkoutPlanValidation workoutPlanValidation;
    private final ProfileValidation profileValidation;
    private final WorkoutPlanPromptBuilder promptBuilder = new WorkoutPlanPromptBuilder();
    private final WorkoutPlanAIResponseParser responseParser = new WorkoutPlanAIResponseParser();

    @Autowired
    public WorkoutPlanAIGenerator(
            TrainingGoalService trainingGoalService,
            WorkoutPlanService workoutPlanService,
            WorkoutPlanValidation workoutPlanValidation,
            ProfileValidation profileValidation,
            @Qualifier("trainingChatClient") ChatClient chatClient) {

        this.trainingGoalService = trainingGoalService;
        this.workoutPlanService = workoutPlanService;
        this.workoutPlanValidation = workoutPlanValidation;
        this.profileValidation = profileValidation;
        this.chatClient = chatClient;
    }

    public WorkoutPlanResponseDTO generatePlan(UUID requesterId) {
        TrainingGoalResponseDTO goalDto = trainingGoalService.getTrainingGoalByUserId(requesterId);

        Profile profile = null;
        if (profileValidation.existsById(requesterId)) {
            profile = profileValidation.validateProfileById(requesterId);
        }

        String inputJson = promptBuilder.buildInputJson(goalDto, profile);

        String aiResponse = chatClient.prompt()
                .user(inputJson)
                .call()
                .content();

        if (workoutPlanValidation.noExistsWorkoutPlanById(goalDto.getId())) {
            var requestDto = responseParser.parse(aiResponse, goalDto.getId());
            return workoutPlanService.createWorkoutPlan(requestDto);
        }

        var requestDto = responseParser.parse(aiResponse);
        requestDto.setTrainingGoalId(goalDto.getId());
        return workoutPlanService.updateWorkoutPlan(requestDto);

    }

}
