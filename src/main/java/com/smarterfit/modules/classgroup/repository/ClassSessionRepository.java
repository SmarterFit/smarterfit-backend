package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, UUID> {

    List<ClassSession> findAllSessionsByClassGroupId(UUID classGroupId);

    @Query("""
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
    FROM ClassSession s
    WHERE s.classGroup.id = :classGroupId
      AND s.id <> :classSessionId
      AND (
          s.startTime < :endTime AND s.endTime > :startTime
      )
""")
    boolean existsByDateRangeAndClassGroupId(
            UUID classGroupId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            UUID classSessionId
    );


    @Query("""
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
    FROM ClassSession s
    WHERE s.classGroup.id = :classGroupId
      AND (
          s.startTime < :endTime AND s.endTime > :startTime
      )
""")
    boolean existsByDateRangeAndClassGroupId(
            UUID classGroupId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

}
