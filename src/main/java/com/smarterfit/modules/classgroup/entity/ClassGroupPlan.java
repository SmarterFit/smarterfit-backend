package com.smarterfit.modules.classgroup.entity;

import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.classgroup.entity.id.ClassGroupPlanId;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "classGroup", "plan" })
@IdClass(ClassGroupPlanId.class)
@Entity
@Table(name = "SF_CLASS_GROUP_PLAN")
public class ClassGroupPlan {
    @Id
    @JoinColumn(name = "class_group_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ClassGroup classGroup;

    @Id
    @JoinColumn(name = "plan_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;
}
