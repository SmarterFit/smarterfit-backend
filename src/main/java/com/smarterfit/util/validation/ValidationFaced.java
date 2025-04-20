package com.smarterfit.util.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidationFaced {
    public final ClassGroupValidation classGroupValidation;
    public final ModalityValidation modalityValidation;
    public final UserValidation userValidation;
    public final ClassGroupUserValidation classGroupUserValidation;
    public final ClassGroupPlanValidation classGroupPlanValidation;
    public final PlanValidation planValidation;

    public ValidationFaced(ClassGroupValidation classGroupValidation,
                           ModalityValidation modalityValidation,
                           UserValidation userValidation,
                           ClassGroupUserValidation classGroupUserValidation,
                           ClassGroupPlanValidation classGroupPlanValidation,
                           PlanValidation planValidation) {
        this.classGroupValidation = classGroupValidation;
        this.modalityValidation = modalityValidation;
        this.userValidation = userValidation;
        this.classGroupUserValidation = classGroupUserValidation;
        this.classGroupPlanValidation = classGroupPlanValidation;
        this.planValidation = planValidation;
    }
}
