package com.smarterfit.modules.checkin.event;

import org.springframework.context.ApplicationEvent;

public class AllCheckOutEvent extends ApplicationEvent {

    public AllCheckOutEvent(Object source) {
        super(source);
    }
}
