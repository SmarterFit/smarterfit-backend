package com.smarterfit.modules.checkin.event.listener;

import com.smarterfit.modules.checkin.event.AllCheckOutEvent;
import com.smarterfit.modules.checkin.service.GymCheckInService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AllCheckOutEventListener {

    private final GymCheckInService gymCheckInService;

    public AllCheckOutEventListener(GymCheckInService gymCheckInService) {
        this.gymCheckInService = gymCheckInService;
    }

    @EventListener
    public void handleAllCheckOutEvent(AllCheckOutEvent event) {
        gymCheckInService.doCheckOutInAll();
    }
}
