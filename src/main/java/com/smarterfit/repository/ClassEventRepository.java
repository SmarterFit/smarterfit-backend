package com.smarterfit.repository;

import com.smarterfit.model.ClassEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClassEventRepository extends JpaRepository<ClassEvent, UUID> {
    List<ClassEvent> findAllByClassGroupId(UUID classGroupId);

    boolean existsByClassGroupIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID classGroupId, LocalDateTime endDate, LocalDateTime startDate);


}
