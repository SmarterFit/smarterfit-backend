package com.smarterfit.repository;

import com.smarterfit.model.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClassGroupRepository  extends JpaRepository<ClassGroup, UUID> {

    Optional<ClassGroup> findByTitle(String title);


}
