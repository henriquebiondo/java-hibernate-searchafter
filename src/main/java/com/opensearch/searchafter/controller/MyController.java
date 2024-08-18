package com.opensearch.searchafter.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

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

        for (int i = 0; i <= 10000; i++) {
            ProductEntity product = new ProductEntity();
            product.setProductName("5");
            product.setProductCode("123");
            product.setQuantity(10);
            product.setValor(new BigDecimal(10.0));
            product.setDescription(getRandomName());

            productRepository.saveProduct(product);
            System.out.println("Salvo i = " + i);
        }

        return "salvo com sucesso";
        
    }

    @GetMapping("/find/{name}")
    public List<ProductEntity> findByName(@PathVariable("name") String name, @PathParam("pageSize") int pageSize) {

        List<ProductEntity> list = productRepository.searchByName(name, pageSize);

        return list;
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
