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
import com.domain.onlineshoppingapi.dtos.mapper.IPaymentRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.PaymentParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.request.PaymentRequest;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;
import com.domain.onlineshoppingapi.dtos.response.PaymentResponse;
import com.domain.onlineshoppingapi.dtos.response.ResponseData;
import com.domain.onlineshoppingapi.services.IPaymentSerivce;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    private final IPaymentSerivce paymentService;

    private final IPaymentRequestParamsMapper paymentRequestMapper;

    private final ISearchKeyRequestParamsMapper searchKeyRequestMapper;

    public PaymentController(IPaymentSerivce paymentService, IPaymentRequestParamsMapper paymentRequestMapper, ISearchKeyRequestParamsMapper searchKeyRequestMapper) {
        this.paymentService = paymentService;
        this.paymentRequestMapper = paymentRequestMapper;
        this.searchKeyRequestMapper = searchKeyRequestMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseData<PaymentResponse>> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        ResponseData<PaymentResponse> responseData = new ResponseData<>();
        PaymentParams paymentParams = paymentRequestMapper.paymentRequestToPaymentParams(paymentRequest);
        PaymentResponse paymentResponse = paymentService.processPayment(paymentParams);
        responseData.setStatus(true);
        responseData.setPayload(paymentResponse);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<PaymentResponse>>> findAll() {
        ResponseData<Iterable<PaymentResponse>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(paymentService.findAll());

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<PaymentResponse>> findOne(@PathVariable("id") Long id) {
        ResponseData<PaymentResponse> responseData = new ResponseData<>();
        PaymentResponse paymentResponse = paymentService.findOne(id);
        responseData.setStatus(true);
        responseData.setPayload(paymentResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code")
    public ResponseEntity<ResponseData<PaymentResponse>> findByCode(@RequestBody SearchKeyRequest searchKeyRequest) {
        ResponseData<PaymentResponse> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        PaymentResponse paymentResponse = paymentService.findByCode(searchKeyParams);
        responseData.setStatus(true);
        responseData.setPayload(paymentResponse);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}")
    public ResponseEntity<ResponseData<Iterable<PaymentResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page) {
        ResponseData<Iterable<PaymentResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size);
        responseData.setStatus(true);
        responseData.setPayload(paymentService.findByCodeContains(searchKeyParams, pageable));
        
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/code/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<PaymentResponse>>> findByCodeContains(
        @Valid @RequestBody SearchKeyRequest searchKeyRequest,
        @PathVariable("size") int size, @PathVariable("page") int page,
        @PathVariable("sort") String sort) {
        ResponseData<Iterable<PaymentResponse>> responseData = new ResponseData<>();
        SearchKeyParams searchKeyParams = searchKeyRequestMapper.searchKeyRequestToSearchKeyParams(searchKeyRequest);
        Pageable pageable = PageRequest.of(page, size, Sort.by("code"));
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("code").descending());
        }
        responseData.setStatus(true);
        responseData.setPayload(paymentService.findByCodeContains(searchKeyParams, pageable));
    
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<PaymentResponse>> update(@PathVariable("id") Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
        ResponseData<PaymentResponse> responseData = new ResponseData<>();
        PaymentParams paymentParams = paymentRequestMapper.paymentRequestToPaymentParams(paymentRequest);
        PaymentResponse updatedPayment = paymentService.update(id, paymentParams);
        responseData.setStatus(true);
        responseData.setPayload(updatedPayment);

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<PaymentResponse>> removeOne(@PathVariable("id") Long id) {
        ResponseData<PaymentResponse> responseData = new ResponseData<>();
        PaymentResponse paymentResponse = paymentService.removeOne(id);
        responseData.setStatus(true);
        responseData.setPayload(paymentResponse);

        return ResponseEntity.ok(responseData);
    }
}

 