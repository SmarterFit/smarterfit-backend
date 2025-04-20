package com.smarterfit.repository;

import com.smarterfit.model.ClassGroupSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.UUID;

public interface ClassGroupScheduleRepository extends JpaRepository<ClassGroupSchedule, UUID> {

    @Query("""
    SELECT CASE WHEN COUNT(cgs) > 0 THEN TRUE ELSE FALSE END
    FROM ClassGroupSchedule cgs
    WHERE cgs.classGroup.id = :classGroupId
      AND cgs.dayOfWeek = :dayOfWeek
      AND ((cgs.startTime < :endTime AND cgs.endTime > :startTime))
    """)
    boolean existsOverlappingSchedule(UUID classGroupId, Integer dayOfWeek, LocalTime startTime, LocalTime endTime);

    ;
}
