package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.WorkoutExercises;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExercises, UUID> {

}
