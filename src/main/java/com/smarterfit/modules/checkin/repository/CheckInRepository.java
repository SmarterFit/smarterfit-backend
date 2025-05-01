/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.checkin.entity.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, UUID> {

   // Busca o último check-in aberto (sem checkout) de um usuário
   Optional<CheckIn> findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(UUID userId);

}