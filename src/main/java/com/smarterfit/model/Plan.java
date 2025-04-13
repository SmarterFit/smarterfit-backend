package com.smarterfit.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
   double price;

   @Column(name = "duration", nullable = false)
   int duration; // in days

   @Column(name = "max_users", nullable = false)
   int maxUsers;

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
