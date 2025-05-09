package com.smarterfit.modules.classgroup.event;

import com.smarterfit.modules.classgroup.entity.ClassGroup;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClassGroupDeactivatedEvent extends ApplicationEvent {
    private final ClassGroup classGroup;

    public ClassGroupDeactivatedEvent(ClassGroup classGroup) {
        super(classGroup);
        this.classGroup = classGroup;
    }


}
