package com.smarterfit.modules.billing.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.useraccess.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "subscription")
@Table(name = "SF_SUBSCRIPTION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Subscription {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "owner_id", nullable = false)
   private User owner;

   @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private Set<SubscriptionUser> participants = new HashSet<>();

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "plan_id", nullable = false)
   private Plan plan;

   @Column(name = "dt_started_in")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime startedIn;

   @Column(name = "dt_renewed_in")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime renewedIn;

   @Column(name = "dt_ended_in")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime endedIn;

   @Column(name = "status", nullable = false)
   @Enumerated(EnumType.STRING)
   @Builder.Default
   private SubscriptionStatus status = SubscriptionStatus.PENDING;

   @Column(name = "available_members", nullable = false)
   private Integer availableMembers;

   @Column(name = "available_classes", nullable = false)
   private Integer availableClasses;

   @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   Set<Payment> payments = new HashSet<>();

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
