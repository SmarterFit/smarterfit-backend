package com.smarterfit.repository;

import com.smarterfit.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, UUID> {

    // Busca o último check-in aberto (sem checkout) de um usuário
    Optional<CheckIn> findFirstByUserIdAndCheckoutTimeIsNullOrderByCheckinTimeDesc(UUID userId);

}
