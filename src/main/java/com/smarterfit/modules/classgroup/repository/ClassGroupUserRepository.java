package com.smarterfit.modules.classgroup.repository;

import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;
import com.smarterfit.modules.classgroup.entity.id.ClassGroupUserId;
import com.smarterfit.modules.useraccess.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassGroupUserRepository  extends JpaRepository<ClassGroupUser, ClassGroupUserId> {

    boolean existsByUserIdAndClassGroupId(UUID userId, UUID classGroupId);

    Optional<ClassGroupUser> findByUserIdAndClassGroupId(UUID userId, UUID classGroupId);

    void deleteByUserIdAndClassGroupId(UUID userId, UUID classGroupId);

    @Query("SELECT c.user FROM ClassGroupUser c WHERE c.classGroup.id = :classGroupId")
    List<User> findAllUsersByClassGroupId(@Param("classGroupId") UUID classGroupId);

    @Query("SELECT c.classGroup FROM ClassGroupUser c WHERE c.user.id = :userId")
    List<ClassGroup> findClassGroupsByUserId(@Param("userId") UUID userId);



}
