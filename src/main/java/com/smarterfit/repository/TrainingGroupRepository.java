package com.smarterfit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.TrainingGroup.TrainingGroup;

@Repository
public interface TrainingGroupRepository
      extends JpaRepository<TrainingGroup, UUID>, JpaSpecificationExecutor<TrainingGroup> {
}
