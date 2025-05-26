package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {


}
