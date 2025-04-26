package com.smarterfit.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.TrainingGroup.TrainingGroupUser;
import com.smarterfit.model.TrainingGroup.TrainingGroupUserId;

@Repository
public interface TrainingGroupUserRepository extends JpaRepository<TrainingGroupUser, TrainingGroupUserId> {
   TrainingGroupUser findByTrainingGroupIdAndUserId(UUID trainingGroupId, UUID userId);

   Page<TrainingGroupUser> findByTrainingGroupId(UUID trainingGroupId, Pageable pageable);
}
