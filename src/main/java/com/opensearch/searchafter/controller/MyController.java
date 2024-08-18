package com.opensearch.searchafter.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.opensearch.searchafter.response.PagedResponse;
import com.opensearch.searchafter.response.PagedResponseAfter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.opensearch.searchafter.entity.ProductEntity;
import com.opensearch.searchafter.repository.ProductRepository;

import javax.websocket.server.PathParam;


@RestController
public class MyController {

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/")
    public String getMethodName() {
        return "Chamdo API...";
    }

    @GetMapping("/save")
    public String saveProduct() {
        saveProducts();
        return "salvo com sucesso";
        
    }

    private void saveProducts() {
        for (int i = 0; i <= 300000; i++) {
            ProductEntity product = new ProductEntity();
            product.setProductName("5");
            product.setProductCode("123");
            product.setQuantity(10);
            product.setValor(new BigDecimal(10.0));
            product.setDescription(getRandomName());

            productRepository.saveProduct(product);
            System.out.println("Salvo i = " + i);
        }
    }

    @GetMapping("/find/{name}")
    public PagedResponse<ProductEntity> findByName(@PathVariable("name") String name, @PathParam("page") int page, @PathParam("pageSize") int pageSize) {

        PagedResponse<ProductEntity> response = productRepository.searchByName(name,page,pageSize);

        return response;
    }

    @GetMapping("/find/after/{name}")
    public PagedResponseAfter<ProductEntity> findByNameAfter(@PathVariable("name") String name, @PathParam("lastId") Integer lastId, @PathParam("pageSize") int pageSize) throws Exception {
        Object[] ultId = null;

        if (lastId != null)
            ultId = new Object[]{lastId};

        PagedResponseAfter<ProductEntity> response = productRepository.searchByNameAfter(name, ultId, pageSize);

        return response;
    }


    String getRandomName() {
        int stringLength = 15;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }

        return sb.toString();
    }
    
    
}
