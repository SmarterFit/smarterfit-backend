package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.SubscriptionShortResponseDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.dto.response.UserShortResponseDTO;
import com.smarterfit.enums.RoleType;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.model.UserRole.UserRole;
import com.smarterfit.util.Converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
        private static Set<String> getRoleStrings(Set<UserRole> roles) {
            return roles.stream().map(u -> u.getRoleType().toString()).collect(Collectors.toSet());
        }

        public static UserShortResponseDTO toShortResponse(User user) {
            Set<String> roles = getRoleStrings(user.getRoles());
            return new UserShortResponseDTO(user.getId(), user.getEmail(), roles);
        }

        public static UserResponseDTO toResponse(User user) {
            if (user == null) {
                return null;
            }

            Set<String> roles = getRoleStrings(user.getRoles());

            Set<SubscriptionShortResponseDTO> ownedSubscriptions = user.getOwnedSubscriptions().stream()
                    .map(SubscriptionMapper::toShortResponse).collect(Collectors.toSet());
            Set<SubscriptionShortResponseDTO> participatingSubscriptions = user.getParticipatingSubscriptions().stream()
                    .map(participation -> SubscriptionMapper.toShortResponse(participation.getSubscription()))
                    .collect(Collectors.toSet());

            return new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    roles,
                    ownedSubscriptions,
                    participatingSubscriptions);
        }

        public static User toEntity(UserRequestDTO dto, User user) {
            if (user == null) {
                user = new User();
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
            user.setProfile(profile);

            return user;
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
