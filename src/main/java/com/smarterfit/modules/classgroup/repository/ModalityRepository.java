package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smarterfit.modules.classgroup.entity.Modality;

import java.util.List;
import java.util.UUID;

public interface ModalityRepository extends JpaRepository<Modality, UUID> {

    @Query("select m from Modality m where m.name like %:name%")
    List<Modality> findAllByNameContaining(@Param("name") String name);


    boolean existsByName(String name);
}
