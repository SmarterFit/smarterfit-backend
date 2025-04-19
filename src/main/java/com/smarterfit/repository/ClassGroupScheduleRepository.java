package com.smarterfit.repository;

import com.smarterfit.model.ClassGroupSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassGroupScheduleRepository extends JpaRepository<ClassGroupSchedule, UUID> {
;
}
