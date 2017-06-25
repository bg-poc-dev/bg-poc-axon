package com.bg.poc.bgpocaxon.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Alex Belikov
 */
@Value
public class CreateRefernce {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String value;
}
