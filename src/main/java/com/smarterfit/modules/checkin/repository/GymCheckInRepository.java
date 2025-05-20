/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.checkin.entity.GymCheckIn;

@Repository
public interface GymCheckInRepository extends JpaRepository<GymCheckIn, UUID> {
   Optional<GymCheckIn> findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(UUID userId);

   List<GymCheckIn> findByUserId(UUID userId);

   @Modifying
   @Query("UPDATE GymCheckIn g SET g.checkOutTime = :checkOutTime WHERE g.checkOutTime IS NULL")
   int updateAllCheckOutTime(@Param("checkOutTime") LocalDateTime checkOutTime);
}