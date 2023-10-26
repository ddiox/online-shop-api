package com.domain.onlineshoppingapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.services.OrderService;
import com.domain.onlineshoppingapi.services.ProductService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    
    @PostMapping
    public Order createOrder(@RequestBody List<Long> productIds) {
        // Asumsikan productIds merepresentasikan ID dari produk yang dipilih/diorder
        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productService.findOne(productId);
            if (product != null) {
                products.add(product);
            }
        }
        return orderService.createOrder(products);
    }

    @GetMapping
    public Iterable<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order findOne(@PathVariable("id") Long id) {
        return orderService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id) {
        orderService.removeOne(id);
    }
}
