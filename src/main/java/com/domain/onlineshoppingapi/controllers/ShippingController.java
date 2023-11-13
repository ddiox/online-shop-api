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
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.IShippingRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.param.ShippingParams;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;
import com.domain.onlineshoppingapi.dtos.request.ShippingRequest;
import com.domain.onlineshoppingapi.dtos.response.ResponseData;
import com.domain.onlineshoppingapi.dtos.response.ShippingResponse;
import com.domain.onlineshoppingapi.services.IShippingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {

    private final IShippingService shippingService;

    private final IShippingRequestParamsMapper shippingRequestMapper;

    private final ISearchKeyRequestParamsMapper searchKeyRequestMapper;

    public ShippingController(IShippingService shippingService, IShippingRequestParamsMapper shippingRequestMapper, ISearchKeyRequestParamsMapper searchKeyRequestMapper) {
        this.shippingService = shippingService;
        this.shippingRequestMapper = shippingRequestMapper;
        this.searchKeyRequestMapper = searchKeyRequestMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseData<ShippingResponse>> processShipping(@Valid @RequestBody ShippingRequest shippingRequest) {
        ResponseData<ShippingResponse> responseData = new ResponseData<>();
        ShippingParams shippingParams = shippingRequestMapper.shippingRequestToShippingParams(shippingRequest);
        ShippingResponse shippingResponse = shippingService.processShipping(shippingParams);
        responseData.setStatus(true);
        responseData.setPayload(shippingResponse);

        return ResponseEntity.ok(responseData);
    }
   
    @GetMapping
    public ResponseEntity<ResponseData<Iterable<ShippingResponse>>> findAll() {
        ResponseData<Iterable<ShippingResponse>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(shippingService.findAll());

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ShippingResponse>> findOne(@PathVariable("id") Long id) {
        ResponseData<ShippingResponse> responseData = new ResponseData<>();
        ShippingResponse shippingResponse = shippingService.findOne(id);
        responseData.setStatus(true);
        responseData.setPayload(shippingResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code")
    public ResponseEntity<ResponseData<ShippingResponse>> findByCode(@RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<ShippingResponse> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        ShippingResponse shippingResponse = shippingService.findByCode(searchKeyParams);
        responseData.setStatus(true);
        responseData.setPayload(shippingResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<ShippingResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<ShippingResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(shippingService.findByCodeContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<ShippingResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page,
        @PathVariable("sort") String sort) {
        ResponseData<Iterable<ShippingResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("code").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(shippingService.findByCodeContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ShippingResponse>> update(@PathVariable("id") Long id, @Valid @RequestBody ShippingRequest shippingRequest) {
        ResponseData<ShippingResponse> responseData = new ResponseData<>();
        ShippingParams shippingParams = shippingRequestMapper.shippingRequestToShippingParams(shippingRequest);
        ShippingResponse updatedShipping = shippingService.update(id, shippingParams);
        responseData.setStatus(true);
        responseData.setPayload(updatedShipping);

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<ShippingResponse>> removeOne(@PathVariable("id") Long id) {
        ResponseData<ShippingResponse> responseData = new ResponseData<>();
        ShippingResponse shippingResponse = shippingService.removeOne(id);
        responseData.setStatus(true);
        responseData.setPayload(shippingResponse);

        return ResponseEntity.ok(responseData);
    }
}

