package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.TrainingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TrainingGoalRepository extends JpaRepository<TrainingGoal, UUID> {

}
