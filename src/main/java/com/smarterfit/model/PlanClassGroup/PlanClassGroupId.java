package com.smarterfit.model.PlanClassGroup;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PlanClassGroupId implements Serializable {

    private UUID classGroup;
    private UUID planModality;

    public  PlanClassGroupId() {}

    public  PlanClassGroupId(UUID classGroup, UUID planModality) {
        this.classGroup = classGroup;
        this.planModality = planModality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanClassGroupId that = (PlanClassGroupId) o;
        return Objects.equals(classGroup, that.classGroup) && Objects.equals(planModality, that.planModality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classGroup, planModality);
    }
}
