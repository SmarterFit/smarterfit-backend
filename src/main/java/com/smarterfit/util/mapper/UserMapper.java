package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.enums.RoleType;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.model.UserRole.UserRole;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.util.Converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {


    public static User toEntity(UserRequestDTO dto, User user) {
        if(user == null){
            user = new User();
        }
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUsername(dto.username());

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

        return user;
    }

    public static UserResponseDTO toResponse(User user){
        Set<String> roles = user.getRoles().stream().map(u -> u.getRoleType().toString()).collect(Collectors.toSet());
        return new UserResponseDTO(user.getEmail(), user.getUsername(),  roles);
    }

    public static Set<UserRole> getRolesFromDTO(UserRequestDTO dto, User user) {
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
