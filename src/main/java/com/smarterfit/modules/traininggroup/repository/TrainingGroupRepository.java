package com.smarterfit.modules.traininggroup.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.traininggroup.entity.TrainingGroup;

@Repository
public interface TrainingGroupRepository
            extends JpaRepository<TrainingGroup, UUID>, JpaSpecificationExecutor<TrainingGroup> {
      Boolean existsBySlug(String slug);

      Optional<TrainingGroup> findBySlug(String slug);
}
