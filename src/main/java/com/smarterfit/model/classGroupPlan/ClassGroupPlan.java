package com.smarterfit.model.classGroupPlan;


import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.Plan;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"classGroupId", "planId"})
@IdClass(ClassGroupPlanId.class)
@Entity
@Table(name = "SF_CLASS_GROUP_PLAN")
public class ClassGroupPlan {
    @Id
    @JoinColumn(name = "class_group_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ClassGroup classGroupId;

    @Id
    @JoinColumn(name = "plan_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan planId;



}
