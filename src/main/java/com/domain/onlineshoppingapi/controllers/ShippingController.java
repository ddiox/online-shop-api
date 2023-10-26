package com.domain.onlineshoppingapi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.Shipping;
import com.domain.onlineshoppingapi.services.PaymentService;
import com.domain.onlineshoppingapi.services.ShippingService;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {
    
    @Autowired
    private ShippingService shippingService;

    @Autowired
    private PaymentService paymentService;

    // @PostMapping
    // public Shipping processShipping(@RequestBody Map<String, Long> request){
    //     Long paymentId = request.get("paymentId");
    //     if (paymentId != null) {
    //         Payment payment = paymentService.findOne(paymentId);
    //         if (payment != null) {
    //             return shippingService.processShipping(payment);
    //         }
    //     }
    //     return null;
    // }

    @PostMapping
    public Shipping processShipping(@RequestBody Map<String, Object> request) {
        Integer paymentIdInteger = (Integer) request.get("paymentId");
        String address = (String) request.get("address");
    
        if (paymentIdInteger != null && address != null) {
            Long paymentId = paymentIdInteger.longValue(); 
            Payment payment = paymentService.findOne(paymentId);
            if (payment != null) {
                return shippingService.processShipping(payment, address);
            }
        }
        return null;
    }
}
