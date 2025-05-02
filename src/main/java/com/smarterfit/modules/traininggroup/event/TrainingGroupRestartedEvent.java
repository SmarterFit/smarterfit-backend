package com.smarterfit.modules.traininggroup.event;

import org.springframework.context.ApplicationEvent;

import com.smarterfit.modules.traininggroup.entity.TrainingGroup;

import lombok.Getter;

@Getter
public class TrainingGroupRestartedEvent extends ApplicationEvent {
   private final TrainingGroup trainingGroup;

   public TrainingGroupRestartedEvent(TrainingGroup trainingGroup) {
      super(trainingGroup);
      this.trainingGroup = trainingGroup;
   }
}
