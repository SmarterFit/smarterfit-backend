package com.smarterfit.util.validation;

import com.smarterfit.util.validation.entity.*;
import org.springframework.stereotype.Component;

@Component
public class ValidationFaced {
    public final ClassGroupValidation classGroupValidation;
    public final ModalityValidation modalityValidation;
    public final UserValidation userValidation;
    public final ClassGroupUserValidation classGroupUserValidation;
    public final ClassGroupPlanValidation classGroupPlanValidation;
    public final PlanValidation planValidation;
    public final ClassEventValidation classEventValidation;
    public final ClassEventBookingValidation classEventBookingValidation;

    public ValidationFaced(ClassGroupValidation classGroupValidation,
                           ModalityValidation modalityValidation,
                           UserValidation userValidation,
                           ClassGroupUserValidation classGroupUserValidation,
                           ClassGroupPlanValidation classGroupPlanValidation,
                           PlanValidation planValidation,
                           ClassEventValidation classEventValidation,
                           ClassEventBookingValidation classEventBookingValidation) {
        this.classGroupValidation = classGroupValidation;
        this.modalityValidation = modalityValidation;
        this.userValidation = userValidation;
        this.classGroupUserValidation = classGroupUserValidation;
        this.classGroupPlanValidation = classGroupPlanValidation;
        this.planValidation = planValidation;
        this.classEventValidation = classEventValidation;
        this.classEventBookingValidation = classEventBookingValidation;
    }
}
