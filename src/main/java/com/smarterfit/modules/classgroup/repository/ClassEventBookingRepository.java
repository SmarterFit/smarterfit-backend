package com.smarterfit.modules.classgroup.repository;

import com.smarterfit.modules.classgroup.entity.ClassEventBooking;
import com.smarterfit.modules.classgroup.entity.id.ClassEventBookingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassEventBookingRepository extends JpaRepository<ClassEventBooking, ClassEventBookingId> {

    List<ClassEventBooking> findByClassEventId(UUID classEventId);

    Optional<ClassEventBooking> findByUserIdAndClassEventId(UUID userId, UUID classEventId);

    boolean existsByUserIdAndClassEventId(UUID userId, UUID classEventId);

    int countByClassEventId(UUID classEventId);
}