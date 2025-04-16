package com.smarterfit.model.PlanClassGroup;

import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.PlanModality;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"classGroup", "planModality"})
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PlanClassGroupId.class)

@Entity
@Table(name = "SF_PLAN_CLASS_GROUP")
public class PlanClassGroup {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id", nullable = false)
    private ClassGroup classGroup;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id", nullable = false)
    private PlanModality planModality;



}
