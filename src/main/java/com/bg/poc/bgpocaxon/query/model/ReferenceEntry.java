package com.bg.poc.bgpocaxon.query.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Alex Belikov
 */
@Entity
public class ReferenceEntry {

    @Id
    private String id;
    private String name;
    private String value;
    private boolean active;
    private Date lastUpdate;

    public ReferenceEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
