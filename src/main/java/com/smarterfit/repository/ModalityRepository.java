package com.smarterfit.repository;

import com.smarterfit.model.Modality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ModalityRepository extends JpaRepository<Modality, UUID> {

    @Query("select m from modality m where m.name like %:name%")
    Optional<Modality> findByName(@Param("name") String name);
}
