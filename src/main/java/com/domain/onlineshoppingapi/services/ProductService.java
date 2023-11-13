package com.domain.onlineshoppingapi.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.dtos.mapper.IProductEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final IProductEntityResponseMapper productEntityMapper;

    public ProductService(ProductRepository productRepository, IProductEntityResponseMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    public ProductResponse saveOrUpdate(ProductParams productParams) {
        Product product = new Product();
        product.setCode(productParams.getCode());
        product.setName(productParams.getName());
        product.setDescription(productParams.getDescription());
        product.setPrice(productParams.getPrice());
        productRepository.save(product);

        return productEntityMapper.productToProductResponse(product);
    }

    public Iterable<ProductResponse> findAll() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(productEntityMapper.productToProductResponse(product));
        }

        return productResponses;
    }

    public ProductResponse findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productEntityMapper.productToProductResponse(product);
    }

    public ProductResponse findByName(SearchKeyParams searchKeyParams) {
        Product product = productRepository.findByName(searchKeyParams.getSearchKey()).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productEntityMapper.productToProductResponse(product);
    }

    public ProductResponse findByCode(SearchKeyParams searchKeyParams) {
        Product product = productRepository.findByCode(searchKeyParams.getSearchKey()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        return productEntityMapper.productToProductResponse(product);
    }
    
    public Iterable<ProductResponse> findByNameContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        Iterable<Product> products = productRepository.findByNameContains(searchKeyParams.getSearchKey(), pageable);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(productEntityMapper.productToProductResponse(product));
        }

        return productResponses;
    }


    public Iterable<ProductResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        Iterable<Product> products = productRepository.findByCodeContains(searchKeyParams.getSearchKey(), pageable);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(productEntityMapper.productToProductResponse(product));
        }

        return productResponses;
    }

    public ProductResponse update(Long id, ProductParams productParams) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        existingProduct.setCode(productParams.getCode());
        existingProduct.setName(productParams.getName());
        existingProduct.setDescription(productParams.getDescription());
        existingProduct.setPrice(productParams.getPrice());
        Product updatedProduct = productRepository.save(existingProduct);

        return productEntityMapper.productToProductResponse(updatedProduct);
    }
    
    public ProductResponse removeOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(id);
        
        return productEntityMapper.productToProductResponse(product);
    }
}
