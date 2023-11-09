package com.domain.onlineshoppingapi.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.domain.onlineshoppingapi.dtos.ResponseData;
import com.domain.onlineshoppingapi.dtos.mapper.IProductRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.request.ProductRequest;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.services.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;
    
    private final IProductRequestParamsMapper productRequestMapper;
    
    private final ISearchKeyRequestParamsMapper searchKeyRequestMapper;

    public ProductController(IProductService productService, IProductRequestParamsMapper productRequestMapper, ISearchKeyRequestParamsMapper searchKeyRequestMapper) {
        this.productService = productService;
        this.productRequestMapper =  productRequestMapper;
        this.searchKeyRequestMapper = searchKeyRequestMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseData<Product>> save(@Valid @RequestBody ProductRequest productRequest) {
        ResponseData<Product> responseData = new ResponseData<>();
        ProductParams productParams = productRequestMapper.productRequestToProductParams(productRequest);
        Product product = productService.saveOrUpdate(productParams);
        responseData.setStatus(true);
        responseData.setPayload(product);
        return ResponseEntity.ok(responseData);
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
    public ResponseEntity<ResponseData<Product>> findByName(@Valid @RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<Product> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Product product = productService.findByName(searchKeyParams);

        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
            return ResponseEntity.ok(responseData);
        } 
        throw new ProductNotFoundException("Product not found");
    }

    @PostMapping("/search/name/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByNameContains(@Valid @RequestBody SearchKeyRequest searchKeyRequest,
     @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(productService.findByNameContains(searchKeyParams, pageable));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/name/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByNameContains(@Valid @RequestBody SearchKeyRequest searchKeyRequest,
     @PathVariable("size") int size, @PathVariable("page") int page, @PathVariable("sort") String sort) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        if(sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("name").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.findByNameContains(searchKeyParams, pageable));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code")
    public ResponseEntity<ResponseData<Product>> findByCode(@Valid @RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<Product> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Product product = productService.findByCode(searchKeyParams);
    
        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
            return ResponseEntity.ok(responseData);
        } 
        throw new ProductNotFoundException("Product not found");
    }

    @PostMapping("/search/code/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByCodeContains(@Valid @RequestBody SearchKeyRequest searchKeyRequest,
     @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(productService.findByCodeContains(searchKeyParams, pageable));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByCodeContains(@Valid @RequestBody SearchKeyRequest searchKeyRequest,
     @PathVariable("size") int size, @PathVariable("page") int page, @PathVariable("sort") String sort) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("code"));
        if(sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("code").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.findByCodeContains(searchKeyParams, pageable));
        return ResponseEntity.ok(responseData);
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
