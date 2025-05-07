package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassGroupRepository  extends JpaRepository<ClassGroup, UUID>, JpaSpecificationExecutor<ClassGroup> {

    Optional<ClassGroup> findByTitle(String title);


}
