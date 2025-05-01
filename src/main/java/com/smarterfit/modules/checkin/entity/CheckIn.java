/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.modules.useraccess.entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SF_CHECKIN")
public class CheckIn {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Column(name = "dt_checkin_time", nullable = false, updatable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime checkInTime;

   @Column(name = "dt_checkout_time")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime checkOutTime;

   /// TODO: add updatedAt

   @PrePersist
   public void onPrePersist() {
      this.checkInTime = LocalDateTime.now();
   }
}