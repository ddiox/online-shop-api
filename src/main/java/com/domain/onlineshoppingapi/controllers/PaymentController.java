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
import com.domain.onlineshoppingapi.dto.PaymentRequestData;
import com.domain.onlineshoppingapi.dto.ResponseData;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.services.OrderService;
import com.domain.onlineshoppingapi.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    private ResponseEntity<ResponseData<Payment>> handlePaymentOperation(Order order) {
        Payment processedPayment = paymentService.processPayment(order);
        ResponseData<Payment> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(processedPayment);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Payment>> processPayment(@Valid @RequestBody PaymentRequestData paymentRequestData) {
    Long orderId = paymentRequestData.getOrderId();
    if (orderId != null) {
        Order order = orderService.findOne(orderId);
        if (order != null) {
            return handlePaymentOperation(order);
        }
    }
    throw new OrderNotFoundException("Order not found");
    }

    @GetMapping
    public Iterable<Payment> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public Payment findOne(@PathVariable("id") Long id) {
        return paymentService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id) {
        paymentService.removeOne(id);
    }
}
