package com.domain.onlineshoppingapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.dtos.mapper.IOrderEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.param.OrderParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.OrderResponse;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.OrderRepository;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final IOrderEntityResponseMapper orderEntityMapper;
    
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, IOrderEntityResponseMapper orderEntityMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    public OrderResponse createOrder(OrderParams orderParams) {
        List<Long> productIds = orderParams.getProductIds();
        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
            products.add(product);
        }
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No valid products found with the given IDs");
        }
        Order order = new Order();
        order.setProducts(products);
        order.setCode(orderParams.getCode());
        order.setStatus(orderParams.getStatus());
        orderRepository.save(order);

        return orderEntityMapper.orderToOrderResponse(order);
    }

    public Iterable<OrderResponse> findAll() {
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(orderEntityMapper.orderToOrderResponse(order));
        }

        return orderResponses;
    }

    public OrderResponse findOne(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return orderEntityMapper.orderToOrderResponse(order);
    }

    public OrderResponse findByCode(SearchKeyParams searchKeyParams) {
        Order order = orderRepository.findByCode(searchKeyParams.getSearchKey()).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return orderEntityMapper.orderToOrderResponse(order);
    }

    public Iterable<OrderResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        Iterable<Order> orders = orderRepository.findByCodeContains(searchKeyParams.getSearchKey(), pageable);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(orderEntityMapper.orderToOrderResponse(order));
        }

        return orderResponses;
    }

    public OrderResponse update(Long id, OrderParams orderParams) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("Order not found"));
        List<Long> productIds = orderParams.getProductIds();
        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
            products.add(product);
        }
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No valid products found with the given IDs");
        }
        order.setProducts(products);
        order.setCode(orderParams.getCode());
        order.setStatus(orderParams.getStatus());
        orderRepository.save(order);

        return orderEntityMapper.orderToOrderResponse(order);
    }

    public OrderResponse removeOne(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        orderRepository.delete(order);

        return orderEntityMapper.orderToOrderResponse(order);
    }
}
