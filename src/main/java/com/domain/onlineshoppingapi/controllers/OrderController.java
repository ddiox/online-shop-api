package com.domain.onlineshoppingapi.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.domain.onlineshoppingapi.dto.OrderRequestData;
import com.domain.onlineshoppingapi.dto.ResponseData;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.services.OrderService;
import com.domain.onlineshoppingapi.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private ResponseEntity<ResponseData<Order>> handleOrderOperation(OrderRequestData orderRequestData) {
        // Asumsikan productIds merepresentasikan ID dari produk yang dipilih/diorder
        List<Long> productIds = orderRequestData.getProductIds();
        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productService.findOne(productId);
            if (product != null) {
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No valid products found with the given IDs");
        }
        Order createdOrder = orderService.createOrder(products);
        ResponseData<Order> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(createdOrder);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Order>> createOrder(@Valid @RequestBody OrderRequestData orderRequestData) {
        return handleOrderOperation(orderRequestData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Order>>> findAllOrders() {
        ResponseData<Iterable<Order>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(orderService.findAll());
        return ResponseEntity.ok(responseData);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Order>> findOneOrder(@PathVariable("id") Long id) {
        ResponseData<Order> responseData = new ResponseData<>();
        Order order = orderService.findOne(id);

        if (order != null) {
            responseData.setStatus(true);
            responseData.setPayload(order);
            return ResponseEntity.ok(responseData);
        } 
        throw new OrderNotFoundException("Order not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Order>> removeOneOrder(@PathVariable("id") Long id) {
        ResponseData<Order> responseData = new ResponseData<>();
        Order order = orderService.findOne(id);
    
        if (order != null) {
            orderService.removeOne(id);
            responseData.setStatus(true);
            responseData.setPayload(order);
            return ResponseEntity.ok(responseData);
        } 
        throw new OrderNotFoundException("Order not found");
    }
}





