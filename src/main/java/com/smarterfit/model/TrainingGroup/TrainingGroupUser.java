package com.smarterfit.model.TrainingGroup;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.model.UserRole.User;

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

@Entity(name = "training_group_user")
@Table(name = "SF_TRAINING_GROUP_USER")
@IdClass(TrainingGroupUserId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = { "user", "trainingGroup" })
public class TrainingGroupUser {
   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "training_group_id", nullable = false)
   private TrainingGroup trainingGroup;

   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Column(name = "is_admin", nullable = false)
   private Boolean isAdmin;

   @Column(name = "points", nullable = false)
   @Builder.Default
   private Integer points = 0;

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
