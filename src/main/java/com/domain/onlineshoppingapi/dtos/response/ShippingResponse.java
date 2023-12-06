package com.domain.onlineshoppingapi.dtos.response;

import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.ShippingStatus;
import lombok.Data;

@Data
public class ShippingResponse {
    
    private String code;

    private ShippingStatus status;

    private String address;

    private Payment payment;
}
