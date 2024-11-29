package com.opensearch.searchafter.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.opensearch.searchafter.response.PagedResponse;
import com.opensearch.searchafter.response.PagedResponseAfter;
import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.sort.FieldSortBuilder;
import org.opensearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.opensearch.searchafter.entity.ProductEntity;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestHighLevelClient client;


    @Transactional
    public void saveProduct(ProductEntity product) {
        entityManager.persist(product);
    }

    public PagedResponse<ProductEntity> searchByName(String name, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        SearchSession searchSession = Search.session(entityManager);

        SearchQuery<ProductEntity> query = searchSession.search(ProductEntity.class)
                .where(f -> f.match().field("productName").matching(name))
                .sort(f -> f.field("id").asc())
                .toQuery();

        SearchResult<ProductEntity> result = query.fetch((int) pageable.getOffset(), pageable.getPageSize());
        long totalElements = result.total().hitCount();
        List<ProductEntity> products = result.hits();

        return new PagedResponse<>(products, page, pageSize, totalElements, null);
    }

    public PagedResponseAfter<ProductEntity> searchByNameAfter(String name, Object[] lastId, int pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest("tbl_product-000001"); // replace with your index name
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("productName", name));
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));

        if (lastId != null) {
            searchSourceBuilder.searchAfter(lastId);
        }

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<ProductEntity> products = Arrays.stream(searchResponse.getHits().getHits())
                .map(SearchHit::getSourceAsString)
                .map(this::convertJsonToProductEntity) 
                .collect(Collectors.toList());

        Object[] newLastId = new Object[] {searchResponse.getHits().getHits()[searchResponse.getHits().getHits().length - 1].getSortValues()[0]};

        return new PagedResponseAfter<>(products, 0, pageSize, searchResponse.getHits().getTotalHits().value, newLastId);
    }

    private ProductEntity convertJsonToProductEntity(String s) {
        String productName = s.split(",")[0].split(":")[1];

        ProductEntity product = new ProductEntity();
        product.setProductCode(productName);


        return product;
    }


}
