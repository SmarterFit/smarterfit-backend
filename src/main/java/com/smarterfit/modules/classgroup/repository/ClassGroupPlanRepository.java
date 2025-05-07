package com.smarterfit.modules.classgroup.repository;

import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.entity.id.ClassGroupPlanId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassGroupPlanRepository extends JpaRepository<ClassGroupPlan, ClassGroupPlanId> {

    boolean existsByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);

    void deleteByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);

    Optional<ClassGroupPlan> findByPlanIdAndClassGroupId(UUID planId, UUID classGroupId);
}
