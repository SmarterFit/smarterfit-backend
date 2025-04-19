package com.smarterfit.model.classGroupPlan;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ClassGroupPlanId implements Serializable {
    private UUID planId;
    private UUID classGroupId;

    public ClassGroupPlanId() {}

    public ClassGroupPlanId(UUID planId, UUID classGroupId) {
        this.planId = planId;
        this.classGroupId = classGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassGroupPlanId that = (ClassGroupPlanId) o;
        return Objects.equals(planId, that.planId) && Objects.equals(classGroupId, that.classGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, classGroupId);
    }
}
