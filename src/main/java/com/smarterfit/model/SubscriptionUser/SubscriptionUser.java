package com.smarterfit.model.SubscriptionUser;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

   @Column(name = "dt_created_at", nullable = false, updatable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @Column(name = "dt_updated_at", nullable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime updatedAt;

   @PrePersist
   public void onPrePersist() {
      this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now();
   }

   @PreUpdate
   public void onPreUpdate() {
      this.updatedAt = LocalDateTime.now();
   }
}
