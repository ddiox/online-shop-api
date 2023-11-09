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

import com.domain.onlineshoppingapi.dtos.PaymentRequestData;
import com.domain.onlineshoppingapi.dtos.ResponseData;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.exception.PaymentNotFoundException;
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
    public ResponseEntity<ResponseData<Iterable<Payment>>> findAll() {
        ResponseData<Iterable<Payment>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(paymentService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Payment>> findOne(@PathVariable("id") Long id) {
        ResponseData<Payment> responseData = new ResponseData<>();
        Payment payment = paymentService.findOne(id);

        if (payment != null) {
            responseData.setStatus(true);
            responseData.setPayload(payment);
            return ResponseEntity.ok(responseData);
        }
        throw new PaymentNotFoundException("Payment not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Payment>> removeOne(@PathVariable("id") Long id) {
        ResponseData<Payment> responseData = new ResponseData<>();
        Payment payment = paymentService.findOne(id);

        if (payment != null) {
            paymentService.removeOne(id);
            responseData.setStatus(true);
            responseData.setPayload(payment);
            return ResponseEntity.ok(responseData);
        }
        throw new PaymentNotFoundException("Payment not found");
    }
}

 