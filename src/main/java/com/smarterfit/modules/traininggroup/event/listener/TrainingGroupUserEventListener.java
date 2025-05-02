package com.smarterfit.modules.traininggroup.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.traininggroup.event.TrainingGroupRestartedEvent;
import com.smarterfit.modules.traininggroup.service.TrainingGroupUserService;

@Component
public class TrainingGroupUserEventListener {
   private final TrainingGroupUserService trainingGroupUserService;

   public TrainingGroupUserEventListener(TrainingGroupUserService trainingGroupUserService) {
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @EventListener
   public void onTrainingGroupRestarted(TrainingGroupRestartedEvent event) {
      trainingGroupUserService.resetPointsByTrainingGroupId(event.getTrainingGroup().getId());
   }
}
