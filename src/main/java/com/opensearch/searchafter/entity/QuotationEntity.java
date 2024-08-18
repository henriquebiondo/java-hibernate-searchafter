package com.opensearch.searchafter.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tbl_quotation")
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quotation_name")
    private String quotationName;

    @Column(name="expiration_date")
    private LocalDate expirationDate;

    @Column(name="delivery_date")
    private LocalDate deliveryDate;

    @Column(name="delivery_price")
    private BigDecimal deliveryValue;

    @Column(name="description")
    private String description;

    @Column(name="observation")
    private String observation;
    
}
