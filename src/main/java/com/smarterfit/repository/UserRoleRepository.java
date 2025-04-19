package com.smarterfit.repository;
import com.smarterfit.model.userRole.UserRole;
import com.smarterfit.model.userRole.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
