package com.bg.poc.bgpocaxon.query;

import com.bg.poc.bgpocaxon.domain.events.ReferenceDeleted;
import com.bg.poc.bgpocaxon.domain.events.ReferenceUpdated;
import com.bg.poc.bgpocaxon.domain.events.RefernceCreated;
import com.bg.poc.bgpocaxon.query.model.ReferenceEntry;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author Alex Belikov
 */
@Component
public class ReferenceEventHandler {


    @Autowired
    private ReferenceEntryRepository repository;

    @EventHandler
    @Transactional
    public void on(RefernceCreated event) {
        ReferenceEntry entry = new ReferenceEntry();
        entry.setId(event.getId());
        entry.setName(event.getName());
        entry.setValue(event.getValue());
        entry.setLastUpdate(new Date());
        entry.setActive(true);
        repository.save(entry);
    }

    @EventHandler
    @Transactional
    public void on(ReferenceUpdated event) {
        Optional<ReferenceEntry> referenceEntry = Optional.ofNullable(repository.findOne(event.getId()));
        referenceEntry.ifPresent(
                entry -> {
                    entry.setId(event.getId());
                    entry.setName(event.getName());
                    entry.setValue(event.getValue());
                    entry.setLastUpdate(new Date());
                    repository.save(entry);
                }
        );
    }

    @EventHandler
    @Transactional
    public void on(ReferenceDeleted event) {
        Optional<ReferenceEntry> referenceEntry = Optional.ofNullable(repository.findOne(event.getId()));
        referenceEntry.ifPresent(ref -> {
            ref.setLastUpdate(new Date());
            ref.setActive(false);
            repository.save(ref);
        });
    }
}

