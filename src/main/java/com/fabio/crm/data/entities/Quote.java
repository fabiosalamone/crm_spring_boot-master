package com.fabio.crm.data.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.persistence.*;


@Entity
@Table(name = "Quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String description;
    @Column
    @NotNull
    private int price;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Quote(String description, int price, Customer customer) {
        this.description = description;
        this.price = price;
        this.customer = customer;
    }


    public Quote() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
