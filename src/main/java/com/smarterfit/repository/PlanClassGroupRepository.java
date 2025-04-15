package com.smarterfit.repository;

import com.smarterfit.model.PlanClassGroup.PlanClassGroup;
import com.smarterfit.model.PlanClassGroup.PlanClassGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanClassGroupRepository  extends JpaRepository<PlanClassGroup, PlanClassGroupId> {

}
