package com.smarterfit.common.scheduler;

import com.smarterfit.modules.classgroup.service.ClassEventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClassEventScheduler {

    private final ClassEventService classEventService;

    public ClassEventScheduler(ClassEventService classEventService){
        this.classEventService = classEventService;
    }

    @Scheduled(cron = "0 0 2 * * *") // Every day 02 AM
    public void eventFinished() {classEventService.updateFinishedEvents();}
}
