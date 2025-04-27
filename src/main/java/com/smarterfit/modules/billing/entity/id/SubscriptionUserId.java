package com.smarterfit.modules.billing.entity.id;

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
@EqualsAndHashCode(of = { "user", "subscription" })
@Embeddable
public class SubscriptionUserId {
   private UUID user;
   private UUID subscription;
}
