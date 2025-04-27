package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassGroup;

import java.util.Optional;
import java.util.UUID;

public interface ClassGroupRepository  extends JpaRepository<ClassGroup, UUID> {

    Optional<ClassGroup> findByTitle(String title);


}
