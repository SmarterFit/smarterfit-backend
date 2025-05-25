package com.smarterfit.modules.checkin.entity.id;

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
@EqualsAndHashCode(of = { "user", "classSession" })
@Embeddable
public class ClassCheckInId {
   private UUID user;
   private UUID classSession;
}
