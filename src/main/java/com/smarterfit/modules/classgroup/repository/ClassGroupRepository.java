package com.smarterfit.modules.classgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.classgroup.entity.ClassGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, UUID>, JpaSpecificationExecutor<ClassGroup> {
    Optional<ClassGroup> findByTitle(String title);

    @Query("SELECT COUNT(cg) > 0 FROM ClassGroup cg WHERE cg.isActive = true AND cg.modality.id = :modalityId")
    boolean classGroupHasModality(@Param("modalityId") UUID modalityId);

    List<ClassGroup> findAllByIsActiveTrue();

    @Query("""
            SELECT DISTINCT cg
            FROM SubscriptionUser su
            JOIN su.subscription s
            JOIN s.plan p
            JOIN p.classGroupPlans cgp
            JOIN cgp.classGroup cg
            WHERE su.user.id = :userId
              AND s.status = 'ACTIVE'
              AND s.availableClasses > 0
            """)
    List<ClassGroup> findAvailableClassGroupsByUser(@Param("userId") UUID userId);

    boolean existsBySlug(String slug);

}
