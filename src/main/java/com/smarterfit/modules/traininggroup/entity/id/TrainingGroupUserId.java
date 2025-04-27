package com.smarterfit.modules.traininggroup.entity.id;

import java.util.UUID;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class TrainingGroupUserId {
   private UUID trainingGroup;
   private UUID user;
}
