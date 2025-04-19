package com.smarterfit.model.classGroupUser;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ClassGroupUserId implements Serializable {

    private UUID classGroup;
    private UUID user;

    public ClassGroupUserId() {}

    public ClassGroupUserId(UUID classGroup, UUID planModality) {
        this.classGroup = classGroup;
        this.user = planModality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassGroupUserId that = (ClassGroupUserId) o;
        return Objects.equals(classGroup, that.classGroup) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classGroup, user);
    }
}
