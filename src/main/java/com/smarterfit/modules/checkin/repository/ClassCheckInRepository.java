package com.smarterfit.modules.checkin.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.checkin.entity.ClassCheckIn;
import com.smarterfit.modules.checkin.entity.id.ClassCheckInId;

@Repository
public interface ClassCheckInRepository extends JpaRepository<ClassCheckIn, ClassCheckInId> {
   List<ClassCheckIn> findByUserId(UUID userId);

   List<ClassCheckIn> findByClassSessionId(UUID classSessionId);
}
