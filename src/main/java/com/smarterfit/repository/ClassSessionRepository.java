package com.smarterfit.repository;

import com.smarterfit.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassSessionRepository extends JpaRepository<ClassSession, UUID> {
}
