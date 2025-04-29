package com.smarterfit.modules.traininggroup.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.traininggroup.entity.TrainingGroupUser;
import com.smarterfit.modules.traininggroup.entity.id.TrainingGroupUserId;

@Repository
public interface TrainingGroupUserRepository extends JpaRepository<TrainingGroupUser, TrainingGroupUserId> {
   // TODO: Retornar Subscriptions e Users diretamente

   List<TrainingGroupUser> findByTrainingGroupId(UUID trainingGroupId);

   List<TrainingGroupUser> findByUserId(UUID userId);

   Boolean existsByTrainingGroupIdAndUserId(UUID trainingGroupId, UUID userId);

   Boolean existsByTrainingGroupIdAndIsAdmin(UUID trainingGroupId, boolean isAdmin);

   @Modifying
   @Query("UPDATE training_group_user t SET t.points = 0 WHERE t.trainingGroup.id = :trainingGroupId")
   void resetPointsByTrainingGroupId(UUID trainingGroupId);
}
