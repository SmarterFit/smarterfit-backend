package com.smarterfit.repository;

import com.smarterfit.model.classSessionBooking.ClassSessionBooking;
import com.smarterfit.model.classSessionBooking.ClassSessionBookingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ClassSessionBookingRepository extends JpaRepository<ClassSessionBooking, ClassSessionBookingId> {

    List<ClassSessionBooking> findByClassSessionId(UUID classSessionId);

    Optional<ClassSessionBooking> findByUserIdAndClassSessionId(UUID userId, UUID classSessionId);

    boolean existsByUserIdAndClassSessionId(UUID userId, UUID classSessionId);
}