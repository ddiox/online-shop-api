package com.domain.onlineshoppingapi.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.domain.onlineshoppingapi.dtos.mapper.IProductRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.request.ProductRequest;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;
import com.domain.onlineshoppingapi.dtos.response.ResponseData;
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
    public ResponseEntity<ResponseData<ProductResponse>> save(@Valid @RequestBody ProductRequest productRequest) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        ProductParams productParams = productRequestMapper.productRequestToProductParams(productRequest);
        ProductResponse productResponse = productService.saveOrUpdate(productParams);
        responseData.setStatus(true);
        responseData.setPayload(productResponse);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<ProductResponse>>> findAll() {
        ResponseData<Iterable<ProductResponse>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(productService.findAll());

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> findOne(@PathVariable("id") Long id) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        ProductResponse productResponse = productService.findOne(id);
        responseData.setStatus(true);
        responseData.setPayload(productResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/name")
    public ResponseEntity<ResponseData<ProductResponse>> findByName(@Valid @RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        ProductResponse productResponse = productService.findByName(searchKeyParams);
        responseData.setStatus(true);
        responseData.setPayload(productResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code")
    public ResponseEntity<ResponseData<ProductResponse>> findByCode(@Valid @RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        ProductResponse productResponse = productService.findByCode(searchKeyParams);
        responseData.setStatus(true);
        responseData.setPayload(productResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/name/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<ProductResponse>>> findByNameContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<ProductResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(productService.findByNameContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    
    @PostMapping("/search/name/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<ProductResponse>>> findByNameContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page,
        @PathVariable("sort") String sort) {
        ResponseData<Iterable<ProductResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("name").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.findByNameContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<ProductResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<ProductResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(productService.findByCodeContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<ProductResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page,
        @PathVariable("sort") String sort) {
        ResponseData<Iterable<ProductResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("code"));
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("code").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.findByCodeContains(searchKeyParams, pageable));
    
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        ProductParams productParams = productRequestMapper.productRequestToProductParams(productRequest);
        ProductResponse updatedProduct = productService.update(id, productParams);
        responseData.setStatus(true);
        responseData.setPayload(updatedProduct);

        return ResponseEntity.ok(responseData);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> removeOne(@PathVariable("id") Long id) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        ProductResponse productResponse = productService.removeOne(id);
        responseData.setStatus(true);
        responseData.setPayload(productResponse);
        
        return ResponseEntity.ok(responseData);
    }
}
