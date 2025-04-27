package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassSession;

import java.util.UUID;

public interface ClassSessionRepository extends JpaRepository<ClassSession, UUID> {
}
