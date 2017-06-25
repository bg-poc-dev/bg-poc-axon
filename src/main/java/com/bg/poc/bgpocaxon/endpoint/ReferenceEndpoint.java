package com.bg.poc.bgpocaxon.endpoint;

import com.bg.poc.bgpocaxon.domain.commands.CreateRefernce;
import com.bg.poc.bgpocaxon.domain.commands.DeleteReference;
import com.bg.poc.bgpocaxon.domain.commands.UpdateReference;
import com.bg.poc.bgpocaxon.query.model.ReferenceEntry;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ReferenceEndpoint {

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private JpaRepository<ReferenceEntry, String> repository;

    @PostMapping(path = "/reference")
    public ResponseEntity createRefernce(@RequestParam("name") String name, @RequestParam("value") String value) {
        CreateRefernce createRefernce = new CreateRefernce(identifierFactory.generateIdentifier(), name, value);
        commandGateway.send(createRefernce, LoggingCallback.INSTANCE);
        return ResponseEntity.created(URI.create("/reference/")).build();
    }

    @PutMapping(path = "/reference/{uid}")
    public ResponseEntity updateReference(@PathVariable("uid") String uid, @RequestParam("name") String name, @RequestParam(name = "value", required = false) String value) {
        UpdateReference updateReference = new UpdateReference(uid, name, value);
        commandGateway.send(updateReference);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(path = "/reference/{uid}")
    public ResponseEntity deleteReference(@PathVariable("uid") String uid) {
        DeleteReference deleteReference = new DeleteReference(uid);
        commandGateway.send(deleteReference);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/reference/{uid}")
    public ResponseEntity findById(@PathVariable("uid") String uid) {
        ReferenceEntry entry = repository.findOne(uid);
        return entry != null ? ResponseEntity.ok(entry) : ResponseEntity.notFound().build();
    }
}
