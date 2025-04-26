package com.smarterfit.model.TrainingGroup;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "trainingGroup", "user" })
public class TrainingGroupUserId {
   private UUID trainingGroup;
   private UUID user;
}
