package com.smarterfit.repository;

import com.smarterfit.model.classSessionBooking.ClassSessionBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassSessionBookingRepository extends JpaRepository<ClassSessionBooking, UUID> {
}
