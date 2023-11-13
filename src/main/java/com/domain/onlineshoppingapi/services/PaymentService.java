package com.domain.onlineshoppingapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.dtos.mapper.IPaymentEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.param.PaymentParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.PaymentResponse;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.OrderRepository;
import com.domain.onlineshoppingapi.models.repos.PaymentRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService implements IPaymentSerivce {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final IPaymentEntityResponseMapper paymentEntityMapper;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, IPaymentEntityResponseMapper paymentEntityMapper) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentEntityMapper = paymentEntityMapper;
    }

    public double calculateTotalAmount(PaymentParams paymentParams) {
        Long orderId = paymentParams.getOrderId();
        if (orderId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));
            List<Product> products = order.getProducts();
            double totalAmount = products.stream()
                    .mapToDouble(product -> product.getPrice())
                    .sum();

            return totalAmount;
        }

        throw new OrderNotFoundException("Order not found");
    }

    public PaymentResponse processPayment(PaymentParams paymentParams) {
        Long orderId = paymentParams.getOrderId();
        if(orderId != null) {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
            double totalAmount = calculateTotalAmount(paymentParams);
            Payment payment = new Payment();
            payment.setCode(paymentParams.getCode());
            payment.setStatus(paymentParams.getStatus());
            payment.setAmount(totalAmount);
            payment.setOrder(order);
            paymentRepository.save(payment);

            return paymentEntityMapper.paymentToPaymentResponse(payment);
        }

        throw new OrderNotFoundException("Order not found");
    }
 
    public Iterable<PaymentResponse> findAll() {
        Iterable<Payment> payments = paymentRepository.findAll();
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            paymentResponses.add(paymentEntityMapper.paymentToPaymentResponse(payment));
        }

        return paymentResponses;
    }

    public PaymentResponse findOne(Long id){
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        Payment payment = optionalPayment.orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return paymentEntityMapper.paymentToPaymentResponse(payment);
    }

    public PaymentResponse findByCode(SearchKeyParams searchKeyParams) {
        Payment payment = paymentRepository.findByCode(searchKeyParams.getSearchKey()).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return paymentEntityMapper.paymentToPaymentResponse(payment);
    }

    public Iterable<PaymentResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        Iterable<Payment> payments = paymentRepository.findByCodeContains(searchKeyParams.getSearchKey(), pageable);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : payments) {
            paymentResponses.add(paymentEntityMapper.paymentToPaymentResponse(payment));
        }

        return paymentResponses;
    }

    public PaymentResponse update(Long id, PaymentParams paymentParams) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        Payment payment = optionalPayment.orElseThrow(() -> new OrderNotFoundException("Payment not found"));
    
        payment.setCode(paymentParams.getCode());
        payment.setStatus(paymentParams.getStatus());
    
        if (paymentParams.getOrderId() != null && !paymentParams.getOrderId().equals(payment.getOrder().getId())) {
            Order order = orderRepository.findById(paymentParams.getOrderId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));
            List<Product> products = order.getProducts();
            double totalAmount = products.stream()
                    .mapToDouble(product -> product.getPrice())
                    .sum();
    
            payment.setOrder(order);
            payment.setAmount(totalAmount);
        }
        paymentRepository.save(payment);
    
        return paymentEntityMapper.paymentToPaymentResponse(payment);
    }

    public PaymentResponse removeOne(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Payment not found"));
        paymentRepository.delete(payment);

        return paymentEntityMapper.paymentToPaymentResponse(payment);
    }
}
