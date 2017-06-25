package com.bg.poc.bgpocaxon.domain;

import com.bg.poc.bgpocaxon.domain.commands.CreateRefernce;
import com.bg.poc.bgpocaxon.domain.commands.DeleteReference;
import com.bg.poc.bgpocaxon.domain.commands.UpdateReference;
import com.bg.poc.bgpocaxon.domain.events.ReferenceDeleted;
import com.bg.poc.bgpocaxon.domain.events.ReferenceUpdated;
import com.bg.poc.bgpocaxon.domain.events.RefernceCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * @author Alex Belikov
 */
public class Reference {

    @AggregateIdentifier
    private String id;

    private String name;
    private String value;
    private boolean active;

    public Reference() {
    }

    @CommandHandler
    public Reference(CreateRefernce command) {
        apply(new RefernceCreated(command.getId(), command.getName(), command.getValue()));
    }

    @CommandHandler
    public void on(UpdateReference command) {
        if (!active) {
            throw new IllegalStateException();
        }
        apply(new ReferenceUpdated(command.getId(), command.getName(), command.getValue()));
    }

    @CommandHandler
    public void on(DeleteReference command) {
        apply(new ReferenceDeleted(command.getId()));
    }

    @EventSourcingHandler
    public void on(RefernceCreated event) {
        this.id = event.getId();
        this.name = event.getName();
        this.value = event.getValue();
        this.active = true;
    }

    @EventSourcingHandler
    public void on(ReferenceUpdated event) {
        this.name = event.getName();
        this.value = event.getValue();
    }

    @EventSourcingHandler
    public void on(ReferenceDeleted event) {
        this.id = event.getId();
        this.active = false;
    }
}
