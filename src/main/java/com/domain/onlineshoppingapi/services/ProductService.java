package com.domain.onlineshoppingapi.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findOne(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void removeOne(Long id) {
        productRepository.deleteById(id);
    } 
}
