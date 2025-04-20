package com.smarterfit.model.classGroupPlan;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ClassGroupPlanId implements Serializable {
    private UUID plan;
    private UUID classGroup;

    public ClassGroupPlanId() {}

    public ClassGroupPlanId(UUID plan, UUID classGroup) {
        this.plan = plan;
        this.classGroup = classGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassGroupPlanId that = (ClassGroupPlanId) o;
        return Objects.equals(plan, that.plan) && Objects.equals(classGroup, that.classGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plan, classGroup);
    }
}
