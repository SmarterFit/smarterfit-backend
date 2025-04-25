package com.smarterfit.model.SubscriptionUser;

import com.smarterfit.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SF_SUBSCRIPTION_USER")
@IdClass(SubscriptionUserId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "user", "subscription" })
@Builder
public class SubscriptionUser {
   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "subscription_id", nullable = false)
   private Subscription subscription;
}
