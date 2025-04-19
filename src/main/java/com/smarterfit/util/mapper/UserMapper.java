package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.enums.RoleType;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Profile;
import com.smarterfit.model.User;
import com.smarterfit.model.userRole.UserRole;
import com.smarterfit.util.Converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    public static User toEntity(UserRequestDTO dto) {
        return toEntity(dto, new User());
    }

    public static User toEntity(UserRequestDTO dto, User user) {
        if(user == null){
            throw new ResourceNotFoundException("User not found.");
        }

        user.setEmail(dto.email());
        user.setPassword(dto.password());

        Set<UserRole> userRoles = new HashSet<>();

        if (dto.roles() == null || dto.roles().isEmpty()) {
            RoleType defaultRole = Converter.stringToEnum(RoleType.class, RoleType.CUSTOMER.toString());
            UserRole role = new UserRole();
            role.setUser(user);
            role.setRoleType(defaultRole);
            userRoles.add(role);
        } else {
            userRoles = getRolesFromDTO(dto, user);
        }

        user.setRoles(userRoles);

        // Profile
        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        profile.setCpf(dto.cpf());
        profile.setFullName(dto.name());
        user.setProfile(profile);

        return user;
    }

    public static UserResponseDTO toResponse(User user){
        Set<String> roles = user.getRoles().stream().map(u -> u.getRoleType().toString()).collect(Collectors.toSet());
        return new UserResponseDTO(user.getEmail(), roles,  user.getId());
    }



    private static Set<UserRole> getRolesFromDTO(UserRequestDTO dto, User user) {
        Set<UserRole> userRoles = new HashSet<>();
        if (dto.roles() != null) {
            for (String roleStr : dto.roles()) {
                RoleType roleType = Converter.stringToEnum(RoleType.class, roleStr);
                UserRole role = new UserRole();
                role.setUser(user);
                role.setRoleType(roleType);
                userRoles.add(role);
            }
        }
        return userRoles;
    }

}
