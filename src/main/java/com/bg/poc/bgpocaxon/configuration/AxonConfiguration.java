package com.bg.poc.bgpocaxon.configuration;

import com.bg.poc.bgpocaxon.domain.Reference;
import com.bg.poc.bgpocaxon.notification.NotificationHandler;
import com.bg.poc.bgpocaxon.query.ReferenceEventHandler;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.*;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.spring.config.AnnotationDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alex Belikov
 */
@Configuration
@AnnotationDriven
public class AxonConfiguration {

    @Autowired
    private NotificationHandler notificationHandler;

    @Autowired
    private ReferenceEventHandler referenceEventHandler;

    @Bean
    public EventProcessor companiesEventProcessor() {
        SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("referenceEventProcessor",
                new SimpleEventHandlerInvoker(
                        notificationHandler, referenceEventHandler),
                eventStore());
        eventProcessor.start();

        return eventProcessor;
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
        factory.setCommandBus(commandBus());
        return factory;
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public EventStore eventStore() {
        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
        return eventStore;
    }

    @Bean
    public EventSourcingRepository<Reference> referenceEventSourcingRepository() {
        EventSourcingRepository<Reference> repository = new EventSourcingRepository<>(Reference.class, eventStore());
        return repository;
    }

    @Bean
    public AggregateAnnotationCommandHandler taskCommandHandler() {
        AggregateAnnotationCommandHandler handler = new AggregateAnnotationCommandHandler(Reference.class, referenceEventSourcingRepository());
        handler.subscribe(commandBus());
        return handler;
    }

}
