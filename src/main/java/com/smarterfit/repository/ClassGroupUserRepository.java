package com.smarterfit.repository;

import com.smarterfit.model.classGroupUser.ClassGroupUser;
import com.smarterfit.model.classGroupUser.ClassGroupUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClassGroupUserRepository  extends JpaRepository<ClassGroupUser, ClassGroupUserId> {

    boolean existsByUserIdAndClassGroupId(UUID userId, UUID classGroupId);

    Optional<ClassGroupUser> findByUserIdAndClassGroupId(UUID userId, UUID classGroupId);

    void deleteByUserIdAndClassGroupId(UUID userId, UUID classGroupId);



}
