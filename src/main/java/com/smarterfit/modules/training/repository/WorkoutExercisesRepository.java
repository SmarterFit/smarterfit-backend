package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExercise, UUID> {

    List<WorkoutExercise> findByWorkoutDayId(UUID workoutDayId);


}
