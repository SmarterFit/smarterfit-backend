package com.smarterfit.modules.useraccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smarterfit.modules.useraccess.entity.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByCpf(String cpf);
}
