package com.smarterfit.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.model.SubscriptionUser.Subscription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity(name = "plan")
@Table(name = "SF_PLAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Plan {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   UUID id;

   @Column(name = "name", nullable = false)
   String name;

   @Column(name = "description")
   String description;

   @Column(name = "price", nullable = false)
   Double price;

   @Column(name = "duration", nullable = false)
   Integer duration; // in days

   @Column(name = "max_users", nullable = false)
   Integer maxUsers;

   @Column(name = "max_classes", nullable = false)
   Integer maxClasses;

   @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private Set<Subscription> subscriptions = new HashSet<>();

   @Column(name = "dt_deleted_at")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime deletedAt;

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
