package com.sprinboot.jpa.springboot_jpa_relationship.repositories;

import com.sprinboot.jpa.springboot_jpa_relationship.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
