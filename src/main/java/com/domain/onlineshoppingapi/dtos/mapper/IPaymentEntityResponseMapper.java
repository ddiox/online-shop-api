package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.response.PaymentResponse;
import com.domain.onlineshoppingapi.models.entity.Payment;

@Mapper(componentModel = "spring")
public interface IPaymentEntityResponseMapper {

    public PaymentResponse paymentToPaymentResponse(Payment payment);
    
    public Payment paymentResponseToPayment(PaymentResponse paymentResponse);
} 
