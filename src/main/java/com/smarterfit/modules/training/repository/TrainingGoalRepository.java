package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.TrainingGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrainingGoalRepository extends JpaRepository<TrainingGoal, UUID> {
   Boolean existsByUserId(UUID userId);

   Optional<TrainingGoal> findByUserId(UUID userId);
}
