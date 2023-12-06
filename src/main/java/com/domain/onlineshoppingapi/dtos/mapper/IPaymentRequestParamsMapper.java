package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.param.PaymentParams;
import com.domain.onlineshoppingapi.dtos.request.PaymentRequest;

@Mapper(componentModel = "spring")
public interface IPaymentRequestParamsMapper {

    public PaymentRequest paymentParamsToPaymentRequest(PaymentParams paymentParams);
    
    public PaymentParams paymentRequestToPaymentParams(PaymentRequest paymentRequest);    
} 
