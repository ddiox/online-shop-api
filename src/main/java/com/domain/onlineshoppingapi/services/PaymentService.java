package com.domain.onlineshoppingapi.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.repos.PaymentRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    // Fungsi Menghitung Total Amount yang dibayar
    private double calculateTotalAmount(Order order) {
        // Menjumlahkan harga-harga produk yang ada di order
        return order.getProducts().stream().mapToDouble(product -> product.getPrice()).sum();
    }

    //Fungsi Melakukan Pembayaran
    public Payment processPayment(Order order){
        // Menghitung Total Amount yang dibayar
        double totalAmount = calculateTotalAmount(order);

        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setOrder(order);

        return paymentRepository.save(payment);
    }

    public Iterable<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findOne(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return payment.get();
        }
        return null;
    }
    // public Payment findOne(Long id) {
    //     return paymentRepository.findById(id).orElse(null);
    // }

    public void removeOne(Long id) {
        paymentRepository.deleteById(id);
    }
}
