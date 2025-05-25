/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "PresenceSnapshot")
@Table(name = "SF_PRESENCE_SNAPSHOT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class PresenceSnapshot {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(name = "presence_count", nullable = false)
   private Integer presenceCount;

   @Column(name = "created_at", nullable = false, updatable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @PrePersist
   public void onPrePersist() {
      this.createdAt = LocalDateTime.now();
   }
}