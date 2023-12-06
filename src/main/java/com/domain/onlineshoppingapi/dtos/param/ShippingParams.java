package com.domain.onlineshoppingapi.dtos.param;

import com.domain.onlineshoppingapi.models.entity.ShippingStatus;
import lombok.Data;

@Data
public class ShippingParams {
    
    private Long paymentId;

    private String address;

    private String code;

    private ShippingStatus status;
}
