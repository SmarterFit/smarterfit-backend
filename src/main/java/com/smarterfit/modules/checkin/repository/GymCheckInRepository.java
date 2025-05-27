/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.repository;

import com.smarterfit.modules.checkin.entity.GymCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GymCheckInRepository extends JpaRepository<GymCheckIn, UUID> {
        Optional<GymCheckIn> findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(UUID userId);

        Boolean existsByUserIdAndCheckOutTimeIsNull(UUID userId);

        List<GymCheckIn> findByUserId(UUID userId);

        @Modifying
        @Query("UPDATE GymCheckIn g SET g.checkOutTime = :checkOutTime WHERE g.checkOutTime IS NULL")
        int updateAllCheckOutTime(@Param("checkOutTime") LocalDateTime checkOutTime);

        @Query("SELECT g FROM GymCheckIn g WHERE g.user.id = :userId AND g.checkInTime BETWEEN :startDate AND :endDate")
        List<GymCheckIn> findByUserIdAndDateBetween(
                        @Param("userId") UUID userId,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        Integer countByCheckOutTimeIsNull();
}