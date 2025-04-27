package com.smarterfit.modules.useraccess.repository;

import com.smarterfit.modules.useraccess.entity.UserRole;
import com.smarterfit.modules.useraccess.entity.id.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
