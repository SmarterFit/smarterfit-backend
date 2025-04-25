package com.smarterfit.repository;

import com.smarterfit.model.classEventBooking.ClassEventBooking;
import com.smarterfit.model.classEventBooking.ClassEventBookingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ClassEventBookingRepository extends JpaRepository<ClassEventBooking, ClassEventBookingId> {

    List<ClassEventBooking> findByClassEventId(UUID classEventId);

    Optional<ClassEventBooking> findByUserIdAndClassEventId(UUID userId, UUID classEventId);

    boolean existsByUserIdAndClassEventId(UUID userId, UUID classEventId);

    int countByClassEventId(UUID classEventId);
}