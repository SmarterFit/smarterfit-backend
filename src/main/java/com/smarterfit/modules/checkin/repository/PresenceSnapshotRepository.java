/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.checkin.entity.PresenceSnapshot;

public interface PresenceSnapshotRepository extends JpaRepository<PresenceSnapshot, UUID> {
   List<PresenceSnapshot> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

   PresenceSnapshot findTopByOrderByCreatedAtDesc();
}