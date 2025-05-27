package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.WorkoutPlan;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {
}
