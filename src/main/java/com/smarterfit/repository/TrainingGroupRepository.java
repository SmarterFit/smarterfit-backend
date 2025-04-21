package com.smarterfit.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.enums.GroupType;
import com.smarterfit.model.TrainingGroup.TrainingGroup;

@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, UUID> {
   List<TrainingGroup> findByGroupTypeIn(List<GroupType> groupTypes);
}
