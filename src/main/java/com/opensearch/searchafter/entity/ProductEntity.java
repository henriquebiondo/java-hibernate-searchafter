package com.opensearch.searchafter.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "tbl_product")
@Indexed
public class ProductEntity {

    @Id
    @GenericField(sortable = Sortable.YES, searchable = Searchable.YES, projectable = Projectable.YES, aggregable = Aggregable.YES)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @FullTextField
    @Column(name = "product_name")
    private String productName;

    @Column(name="quantity")
    private int quantity;

    @FullTextField
    @Column(name="cod_product")
    private String productCode;

    @Column(name="price")
    private BigDecimal valor;

    @FullTextField
    @Column(name="description")
    private String description;
    
}
