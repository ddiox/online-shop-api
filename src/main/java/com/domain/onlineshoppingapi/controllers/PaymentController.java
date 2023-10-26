package com.domain.onlineshoppingapi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.services.OrderService;
import com.domain.onlineshoppingapi.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Payment processPayment(@RequestBody Map<String, Long> request) {
        Long orderId = request.get("orderId");
        if (orderId != null) {
            Order order = orderService.findOne(orderId);
            if (order != null) {
                return paymentService.processPayment(order);
            }
        }
        return null;
    }

    @GetMapping
    public Iterable<Payment> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public Payment findOne(@PathVariable("id") Long id) {
        return paymentService.findOne(id);
    }
}
