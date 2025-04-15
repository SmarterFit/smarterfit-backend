package com.smarterfit.repository;

import com.smarterfit.model.PlanModality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanModalityRepository extends JpaRepository<PlanModality, UUID> {

}
