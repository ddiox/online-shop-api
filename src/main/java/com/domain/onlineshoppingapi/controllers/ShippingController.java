package com.domain.onlineshoppingapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.onlineshoppingapi.dtos.ResponseData;
import com.domain.onlineshoppingapi.dtos.ShippingRequestData;
import com.domain.onlineshoppingapi.exception.PaymentNotFoundException;
import com.domain.onlineshoppingapi.exception.ShippingNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.Shipping;
import com.domain.onlineshoppingapi.services.PaymentService;
import com.domain.onlineshoppingapi.services.ShippingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {
    
    @Autowired
    private ShippingService shippingService;

    @Autowired
    private PaymentService paymentService;

    private ResponseEntity<ResponseData<Shipping>> handleShippingOperation(Payment payment, String address) {
        Shipping processedShipping = shippingService.processShipping(payment, address);
        ResponseData<Shipping> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(processedShipping);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Shipping>> processShipping(@Valid @RequestBody ShippingRequestData shippingRequestData) {
        Long paymentId = shippingRequestData.getPaymentId();
        String address = shippingRequestData.getAddress();

        if (paymentId != null) {
            Payment payment = paymentService.findOne(paymentId);
            if (payment != null) {
                return handleShippingOperation(payment, address);
            }
        }
        throw new PaymentNotFoundException("Payment not found");
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Shipping>>> findAll() {
        ResponseData<Iterable<Shipping>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(shippingService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Shipping>> findOne(@PathVariable("id") Long id) {
        ResponseData<Shipping> responseData = new ResponseData<>();
        Shipping shipping = shippingService.findOne(id);

        if (shipping != null) {
            responseData.setStatus(true);
            responseData.setPayload(shipping);
            return ResponseEntity.ok(responseData);
        }
        throw new ShippingNotFoundException("Shipping not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Shipping>> removeOne(@PathVariable("id") Long id) {
        ResponseData<Shipping> responseData = new ResponseData<>();
        Shipping shipping = shippingService.findOne(id);

        if (shipping != null) {
            shippingService.removeOne(id);
            responseData.setStatus(true);
            responseData.setPayload(shipping);
            return ResponseEntity.ok(responseData);
        }
        throw new ShippingNotFoundException("Shipping not found");
    }
}

