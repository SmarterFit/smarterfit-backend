package com.smarterfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.TrainingGroup.TrainingGroupUser;
import com.smarterfit.model.TrainingGroup.TrainingGroupUserId;

@Repository
public interface TrainingGroupUserRepository extends JpaRepository<TrainingGroupUser, TrainingGroupUserId> {

}
