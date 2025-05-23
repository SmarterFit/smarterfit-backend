package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {

    Optional<Exercise> findByName(String name);
    public boolean existsByName(String name);

    public List<Exercise> findByNameContainingIgnoreCase(String name);

    Page<Exercise> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
