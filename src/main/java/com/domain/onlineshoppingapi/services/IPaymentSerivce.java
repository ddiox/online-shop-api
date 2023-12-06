package com.domain.onlineshoppingapi.services;

import org.springframework.data.domain.Pageable;
import com.domain.onlineshoppingapi.dtos.param.PaymentParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.PaymentResponse;

public interface IPaymentSerivce {

    double calculateTotalAmount(PaymentParams paymentParams);

    PaymentResponse processPayment(PaymentParams paymentParams);

    Iterable<PaymentResponse> findAll();

    PaymentResponse findOne(Long id);

    PaymentResponse findByCode(SearchKeyParams searchKeyParams);

    Iterable<PaymentResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable);

    PaymentResponse update(Long id, PaymentParams paymentParams);

    PaymentResponse removeOne(Long id);
} 
   
    