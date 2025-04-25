package com.smarterfit.model.SubscriptionUser;

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
@EqualsAndHashCode(of = { "user", "subscription" })
public class SubscriptionUserId {
   private UUID user;
   private UUID subscription;
}
