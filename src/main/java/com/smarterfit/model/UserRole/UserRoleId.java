package com.smarterfit.model.UserRole;

import com.smarterfit.enums.RoleType;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserRoleId implements Serializable {
    private UUID user;
    private RoleType roleType;

    public UserRoleId() {}

    public UserRoleId(UUID user, RoleType roleType) {
        this.user = user;
        this.roleType = roleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId)) return false;
        UserRoleId that = (UserRoleId) o;
        return user.equals(that.user) && roleType == that.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, roleType);
    }
}