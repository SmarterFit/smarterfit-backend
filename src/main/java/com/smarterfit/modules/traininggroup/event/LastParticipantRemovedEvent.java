package com.smarterfit.modules.traininggroup.event;

import org.springframework.context.ApplicationEvent;

import com.smarterfit.modules.traininggroup.entity.TrainingGroup;

import lombok.Getter;

@Getter
public class LastParticipantRemovedEvent extends ApplicationEvent {
   private final TrainingGroup trainingGroup;

   public LastParticipantRemovedEvent(TrainingGroup trainingGroup) {
      super(trainingGroup);
      this.trainingGroup = trainingGroup;
   }

   public TrainingGroup getTrainingGroup() {
      return trainingGroup;
   }
}