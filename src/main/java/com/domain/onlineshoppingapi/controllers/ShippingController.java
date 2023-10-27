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
import com.domain.onlineshoppingapi.dto.ResponseData;
import com.domain.onlineshoppingapi.dto.ShippingRequestData;
import com.domain.onlineshoppingapi.exception.PaymentNotFoundException;
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
    public Iterable<Shipping> findAll() {
        return shippingService.findAll();
    }

    @GetMapping("/{id}")
    public Shipping findOne(@PathVariable("id") Long id) {
        return shippingService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id) {
        shippingService.removeOne(id);
    }
}
