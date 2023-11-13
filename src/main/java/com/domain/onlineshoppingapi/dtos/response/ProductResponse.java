package com.domain.onlineshoppingapi.dtos.response;

import lombok.Data;

@Data
public class ProductResponse {
    
    private String code;

    private String name;

    private String description;
    
    private Double price;
}
