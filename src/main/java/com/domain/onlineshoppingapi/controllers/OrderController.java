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
import com.domain.onlineshoppingapi.dtos.mapper.IOrderRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.OrderParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.request.OrderRequest;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;
import com.domain.onlineshoppingapi.dtos.response.OrderResponse;
import com.domain.onlineshoppingapi.dtos.response.ResponseData;
import com.domain.onlineshoppingapi.services.IOrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService orderService;

    private final IOrderRequestParamsMapper orderRequestMapper;

    private final ISearchKeyRequestParamsMapper searchKeyRequestMapper;

    public OrderController(IOrderService orderService, IOrderRequestParamsMapper orderRequestMapper, ISearchKeyRequestParamsMapper searchKeyRequestMapper) {
        this.orderService = orderService;
        this.orderRequestMapper = orderRequestMapper;
        this.searchKeyRequestMapper = searchKeyRequestMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseData<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        ResponseData<OrderResponse> responseData = new ResponseData<>();
        OrderParams orderParams = orderRequestMapper.orderRequestToOrderParams(orderRequest);
        OrderResponse orderResponse = orderService.createOrder(orderParams);
        responseData.setStatus(true);
        responseData.setPayload(orderResponse);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<OrderResponse>>> findAll() {
        ResponseData<Iterable<OrderResponse>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(orderService.findAll());

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<OrderResponse>> findOne(@PathVariable("id") Long id) {
        ResponseData<OrderResponse> responseData = new ResponseData<>();
        OrderResponse orderResponse = orderService.findOne(id);
        responseData.setStatus(true);
        responseData.setPayload(orderResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code")
    public ResponseEntity<ResponseData<OrderResponse>> findByCode(@RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<OrderResponse> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        OrderResponse orderResponse = orderService.findByCode(searchKeyParams);
        responseData.setStatus(true);
        responseData.setPayload(orderResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<OrderResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<OrderResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(orderService.findByCodeContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<OrderResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page,
        @PathVariable("sort") String sort) {
        ResponseData<Iterable<OrderResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("code"));
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("code").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(orderService.findByCodeContains(searchKeyParams, pageable));
    
        return ResponseEntity.ok(responseData);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<OrderResponse>> update(@PathVariable("id") Long id, @Valid @RequestBody OrderRequest orderRequest) {
        ResponseData<OrderResponse> responseData = new ResponseData<>();
        OrderParams orderParams = orderRequestMapper.orderRequestToOrderParams(orderRequest);
        OrderResponse updatedOrder = orderService.update(id, orderParams);
        responseData.setStatus(true);
        responseData.setPayload(updatedOrder);

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<OrderResponse>> removeOne(@PathVariable("id") Long id) {
        ResponseData<OrderResponse> responseData = new ResponseData<>();
        OrderResponse orderResponse = orderService.removeOne(id);
        responseData.setStatus(true);
        responseData.setPayload(orderResponse);

        return ResponseEntity.ok(responseData);
    }
}





