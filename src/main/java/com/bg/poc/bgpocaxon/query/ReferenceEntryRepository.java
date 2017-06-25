package com.bg.poc.bgpocaxon.query;

import com.bg.poc.bgpocaxon.query.model.ReferenceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Belikov
 */
@Repository
public interface ReferenceEntryRepository extends JpaRepository<ReferenceEntry, String> {
}
