package com.smarterfit.modules.checkin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class CalculatePointsUserEvent  extends ApplicationEvent {

    private final UUID userId;
    private final Integer points;


    public CalculatePointsUserEvent(UUID userId, Integer points) {
        super(userId);
        this.userId = userId;
        this.points = points;
    }

}
