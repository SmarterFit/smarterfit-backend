package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.model.UserRole.UserRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto, User user) {
        User activateUser = user == null ? new User() : user;

        activateUser.setEmail(dto.email());
        activateUser.setPassword(dto.password());

        Set<UserRole> userRoles = new HashSet<>();
        for (var roleType : dto.roles()) {
            UserRole userRole = new UserRole();
            userRole.setUser(activateUser);
            userRole.setRoleType(roleType);
            userRoles.add(userRole);
        }

        activateUser.getRoles().clear();
        activateUser.getRoles().addAll(userRoles);

        // Profile
        Profile profile = activateUser.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(activateUser);
        }

        profile.setCpf(dto.cpf());
        activateUser.setProfile(profile);

        return activateUser;
    }

    public static User toEntity(UserRequestDTO dto) {
        return toEntity(dto, null);
    }

        public static UserResponseDTO toResponse(User user) {
            if (user == null) {
                return null;
            }

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(role -> role.getRoleType()).collect(Collectors.toSet()));

    }
}
