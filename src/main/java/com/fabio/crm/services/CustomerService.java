package com.fabio.crm.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fabio.crm.data.entities.Customer;
import com.fabio.crm.data.repositories.CustomerRepository;
import com.fabio.crm.data.specifications.CustomerDatatableFilter;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    } 
    
    public Page<Customer> getCustomersForDatatable(String queryString, Pageable pageable) {

        CustomerDatatableFilter customerDatatableFilter = new CustomerDatatableFilter(queryString);

        return customerRepository.findAll(customerDatatableFilter, pageable);
    }

    public Customer getCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		return customer.get();

	}
}
