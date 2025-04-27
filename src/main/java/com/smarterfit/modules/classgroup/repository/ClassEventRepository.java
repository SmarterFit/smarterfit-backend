package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClassEventRepository extends JpaRepository<ClassEvent, UUID> {
    List<ClassEvent> findAllByClassGroupId(UUID classGroupId);

    boolean existsByClassGroupIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID classGroupId, LocalDateTime endDate, LocalDateTime startDate);


}
