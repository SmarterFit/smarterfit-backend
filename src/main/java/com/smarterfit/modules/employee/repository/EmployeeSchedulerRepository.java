package com.smarterfit.modules.employee.repository;

import com.smarterfit.modules.employee.entity.EmployeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface EmployeeSchedulerRepository extends JpaRepository<EmployeeSchedule, UUID> {


    @Query("""
    SELECT CASE WHEN COUNT(es) > 0 THEN TRUE ELSE FALSE END
    FROM EmployeeSchedule es
    WHERE es.user.id = :employeeId
      AND es.dayOfWeek = :dayOfWeek
      AND NOT (
        es.endTime <= :startTime OR es.startTime >= :endTime
      )
""")
    boolean existsOverlappingSchedule(
            @Param("employeeId") UUID employeeId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    List<EmployeeSchedule> findAllByUserId(UUID userId);
}
