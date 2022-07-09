package com.fabio.crm.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fabio.crm.data.entities.Quote;
import com.fabio.crm.data.repositories.QuoteRepository;
import com.fabio.crm.data.specifications.QuoteDatatableFilter;

@Service
public class QuoteService {

    private final QuoteRepository QuoteRepository;

    @Autowired
    public QuoteService(QuoteRepository QuoteRepository) {
        this.QuoteRepository = QuoteRepository;
    } 
    
    public Page<Quote> getQuotesForDatatable(String queryString, Pageable pageable) {

        QuoteDatatableFilter QuoteDatatableFilter = new QuoteDatatableFilter(queryString);

        return QuoteRepository.findAll(QuoteDatatableFilter, pageable);
    }

    
}
