package com.opensearch.searchafter.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import com.opensearch.searchafter.entity.ProductEntity;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void saveProduct(ProductEntity product) {
        entityManager.persist(product);
    }

    public List<ProductEntity> searchByName(String name, int pageSize) {

        SearchSession searchSession = Search.session(entityManager);

        SearchQuery<ProductEntity> query = searchSession.search(ProductEntity.class)
            .where(f -> f.match().field("productName").matching(name))
                .sort(f -> f.field("id").asc())
            .toQuery();

        SearchResult<ProductEntity> result = query.fetch(pageSize);

        List<ProductEntity> products = result.hits();

        return products;
    }

    
}
