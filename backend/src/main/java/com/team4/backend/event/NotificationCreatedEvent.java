package com.team4.backend.event;

import org.springframework.context.ApplicationEvent;

public class NotificationCreatedEvent extends ApplicationEvent {

    public NotificationCreatedEvent(Object source) {
        super(source);
    }

}
