package com.smarterfit.modules.useraccess.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.ProfileMetricType;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "profileMetric")
@Table(name = "SF_PROFILE_METRIC")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProfileMetric {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "profile_id", nullable = false)
   private Profile profile;

   @Enumerated(EnumType.STRING)
   @Column(name = "type", nullable = false)
   private ProfileMetricType type;

   @Column(name = "value", nullable = false)
   private Double value;

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
