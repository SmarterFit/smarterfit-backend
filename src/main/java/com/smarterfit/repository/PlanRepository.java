package com.smarterfit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, UUID> {

}
