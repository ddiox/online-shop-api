package com.domain.onlineshoppingapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(List<Product> products) {
        Order order = new Order();
        order.setProducts(products);

        return orderRepository.save(order);
    }

    public Order findOne(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        return null;
    }

    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    public void removeOne(Long id) {
        orderRepository.deleteById(id);
    }
}
