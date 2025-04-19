package com.smarterfit.repository;

import com.smarterfit.model.classGroupUser.ClassGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassGroupUserRepository  extends JpaRepository<ClassGroupUser, UUID> {

}
