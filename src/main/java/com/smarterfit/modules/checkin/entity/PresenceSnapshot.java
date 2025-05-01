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

/// FIXME: PresenceSnapshot não representa a presença do aluno e sim a quantidade de pessoas presentes na academia (número inteiro).

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SF_PRESENCE_SNAPSHOT")
public class PresenceSnapshot {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(optional = false)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Column(name = "presence_time", nullable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime presenceTime;

   /// TODO: add createdAt and updatedAt
}