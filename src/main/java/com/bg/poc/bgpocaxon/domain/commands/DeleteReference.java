package com.bg.poc.bgpocaxon.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Alex Belikov
 */
@Value
public class DeleteReference {

    @TargetAggregateIdentifier
    private String id;
}
