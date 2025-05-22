package com.smarterfit.modules.training.repository;

import com.smarterfit.modules.training.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

    @Query("SELECT e FROM Equipment e WHERE e.name = :name")
    public boolean existsByName(@Param("name") String name);

    @Query("select m from Modality m where m.name like %:name%")
    public List<Equipment> findAllByNameContaining(@Param("name") String name);
}
