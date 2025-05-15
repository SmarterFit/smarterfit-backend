package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClassEventRepository extends JpaRepository<ClassEvent, UUID> {
    List<ClassEvent> findAllByClassGroupId(UUID classGroupId);

    boolean existsByClassGroupIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID classGroupId, LocalDateTime endDate, LocalDateTime startDate);

    List<ClassEvent> findAllByFinishedFalse();

    @Query("SELECT e FROM ClassEvent e WHERE e.finished = false")
        List<ClassEvent> findAllUnfinishedEvents();


    @Query("""
    SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
    FROM ClassEvent e
    WHERE e.classGroup.id = :classGroupId
      AND e.id <> :currentEventId
      AND (
          (e.startDate < :endDate AND e.endDate > :startDate)
      )
""")
    boolean existsByDateRangeAndClassGroupExceptCurrent(
            LocalDateTime startDate,
            LocalDateTime endDate,
            UUID classGroupId,
            UUID currentEventId
    );

}
