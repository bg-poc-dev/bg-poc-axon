package com.bg.poc.bgpocaxon.domain.events;

import lombok.Value;

/**
 * @author Alex Belikov
 */
@Value
public class ReferenceUpdated {

    private final String id;
    private final String name;
    private final String value;
}
