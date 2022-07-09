package com.fabio.crm.data.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fabio.crm.data.entities.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
