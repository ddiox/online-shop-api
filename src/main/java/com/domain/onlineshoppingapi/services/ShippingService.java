package com.domain.onlineshoppingapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.Shipping;
import com.domain.onlineshoppingapi.models.repos.ShippingRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    // public Shipping processShipping(Payment payment){
    //     Shipping shipping = new Shipping();
    //     shipping.setPayment(payment);
    //     return shippingRepository.save(shipping);
    // }
    
    public Shipping processShipping(Payment payment, String address) {
        Shipping shipping = new Shipping();
        shipping.setPayment(payment);
        shipping.setAddress(address);
        return shippingRepository.save(shipping);
    }
}

