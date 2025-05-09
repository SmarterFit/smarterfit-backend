package com.smarterfit.modules.classgroup.event.listener;

import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.service.ClassGroupUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassGroupEventListener {

    private final ClassGroupUserService classGroupUserService;

    public ClassGroupEventListener(ClassGroupUserService classGroupUserService) {
        this.classGroupUserService = classGroupUserService;
    }

    @EventListener
    public void handleClassGroupDeactivated(ClassGroupDeactivatedEvent event) {

        classGroupUserService.removeSubscriptionByClassGroup(event.getClassGroup());
    }
}
