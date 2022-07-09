package com.fabio.crm.data.repositories;
import com.fabio.crm.data.entities.Quote;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long>, JpaSpecificationExecutor<Quote>{
    
}
