package com.bg.poc.bgpocaxon.domain.events;

import lombok.Value;

/**
 * @author Alex Belikov
 */
@Value
public class RefernceCreated {

    private final String id;
    private final String name;
    private final String value;
}
