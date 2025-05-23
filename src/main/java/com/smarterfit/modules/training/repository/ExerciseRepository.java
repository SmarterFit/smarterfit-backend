package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {


}
