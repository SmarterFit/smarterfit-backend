package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.WorkoutDay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, UUID> {


}
