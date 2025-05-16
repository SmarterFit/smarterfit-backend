package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.useraccess.dto.request.user.CreateUserRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.entity.UserRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    public static User toEntity(CreateUserRequestDTO dto, Profile profile) {
        return toEntity(dto, profile, new User());
    }

    public static User toEntity(CreateUserRequestDTO dto, Profile profile, User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found.");
        }

        user = GenericMapper.map(dto, user);

        // Roles
        Set<UserRole> userRoles = new HashSet<>();
        for (RoleType role : dto.getRoles()) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRoleType(role);
            userRoles.add(userRole);
        }

        user.getRoles().clear();
        user.getRoles().addAll(userRoles);

        // Profile
        profile.setUser(user);
        profile.setFullName(dto.getName());
        profile.setCpf(dto.getCpf());
        user.setProfile(profile);

        return user;
    }

    public static UserResponseDTO toResponse(User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        UserResponseDTO response = GenericMapper.map(user, UserResponseDTO.class);

        response = response.toBuilder().roles(
                user.getRoles().stream().map(role -> role.getRoleType()).collect(Collectors.toSet())).build();

        return response;
    }
}
