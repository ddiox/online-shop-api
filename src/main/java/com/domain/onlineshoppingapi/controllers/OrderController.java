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
import com.domain.onlineshoppingapi.dto.ResponseData;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.services.OrderService;
import com.domain.onlineshoppingapi.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
  
    private ResponseEntity<ResponseData<Order>> handleOrderOperation(List<Long> productIds) {
        // Asumsikan productIds merepresentasikan ID dari produk yang dipilih/diorder
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
    public ResponseEntity<ResponseData<Order>> createOrder(@Valid @RequestBody @NotEmpty(message = "Product IDs are required") List<Long> productIds) {
        return handleOrderOperation(productIds);
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
