package com.domain.onlineshoppingapi.services;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveOrUpdate(ProductParams productParams) {
        Product product = new Product();
        product.setCode(productParams.getCode());
        product.setName(productParams.getName());
        product.setDescription(productParams.getDescription());
        product.setPrice(productParams.getPrice());

        return productRepository.save(product);
    }

    public Product findOne(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public Product findByName(SearchKeyParams searchKeyParams) {
        Optional<Product> product = productRepository.findByName(searchKeyParams.getSearchKey());
        if(product.isPresent()) {
            return product.get();
        }
        return null;
    }
    
    public Product findByCode(SearchKeyParams searchKeyParams) {
        Optional<Product> product = productRepository.findByCode(searchKeyParams.getSearchKey());
        if(product.isPresent()) {
            return product.get();
        }
        return null;
    }
    
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }
 
    public Iterable<Product> findByNameContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        return productRepository.findByNameContains(searchKeyParams.getSearchKey(), pageable);
    }

    public Iterable<Product> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        return productRepository.findByCodeContains(searchKeyParams.getSearchKey(), pageable);
    }

    public void removeOne(Long id) {
        productRepository.deleteById(id);
    }
}
