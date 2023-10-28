package com.domain.onlineshoppingapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.domain.onlineshoppingapi.dto.ProductRequestData;
import com.domain.onlineshoppingapi.dto.ResponseData;
import com.domain.onlineshoppingapi.dto.SearchData;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Product convertDTOToEntity(ProductRequestData productRequestData) {
        Product product = new Product();
    
        if (productRequestData.getId() != null) {
            // Untuk proses Update mengambil Id yang dikirim dari RequestBody
            product.setId(productRequestData.getId());
        }
    
        product.setName(productRequestData.getName());
        product.setDescription(productRequestData.getDescription());
        product.setPrice(productRequestData.getPrice());
    
        return product;
    }

    private ResponseEntity<ResponseData<Product>> handleProductOperation(ProductRequestData productRequestData) {
        ResponseData<Product> responseData = new ResponseData<>();
        Product product = convertDTOToEntity(productRequestData);
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody ProductRequestData productRequestData) {
        return handleProductOperation(productRequestData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody ProductRequestData productRequestData) {
        return handleProductOperation(productRequestData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Product>>> findAll() {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(productService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> findOne(@PathVariable("id") Long id) {
        ResponseData<Product> responseData = new ResponseData<>();
        Product product = productService.findOne(id);

        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
            return ResponseEntity.ok(responseData);
        } 
        throw new ProductNotFoundException("Product not found");
    }

    @PostMapping("/search/name")
    public ResponseEntity<ResponseData<Product>> findByName(@RequestBody SearchData searchData) {
        ResponseData<Product> responseData = new ResponseData<>();
        Product product = productService.findByName(searchData.getSearchKey());

        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
            return ResponseEntity.ok(responseData);
        } 
        throw new ProductNotFoundException("Product not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> removeOne(@PathVariable("id") Long id) {
        ResponseData<Product> responseData = new ResponseData<>();
        Product product = productService.findOne(id);
    
        if (product != null) {
            productService.removeOne(id);
            responseData.setStatus(true);
            responseData.setPayload(product);
            return ResponseEntity.ok(responseData);
        } 
        throw new ProductNotFoundException("Product not found");
    }
}