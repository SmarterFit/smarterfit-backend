package com.smarterfit.repository;

import com.smarterfit.model.classGroupPlan.ClassGroupPlan;
import com.smarterfit.model.classGroupPlan.ClassGroupPlanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClassGroupPlanRepository extends JpaRepository<ClassGroupPlan, ClassGroupPlanId> {

    boolean existsByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);

    void deleteByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);

    Optional<ClassGroupPlan> findByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);
}
