package com.bg.poc.bgpocaxon.notification;

import com.bg.poc.bgpocaxon.domain.events.RefernceCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * @author Alex Belikov
 */
@Component
public class NotificationHandler {

    @EventHandler
    public void onCreate(RefernceCreated event) {
        System.out.println("notify " + event.getId());
    }

}
