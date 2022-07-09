package com.fabio.crm.data.specifications;

import javax.persistence.criteria.*;

import com.fabio.crm.data.entities.Quote;

import java.util.ArrayList;

public class QuoteDatatableFilter implements org.springframework.data.jpa.domain.Specification<Quote>{
    
    String userQuery;

    public QuoteDatatableFilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Quote> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (userQuery != null && userQuery != "") {
            predicates.add(criteriaBuilder.like(root.get("description"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("price"), '%' + userQuery + '%'));
        }

        return (! predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}
