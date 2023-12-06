package com.domain.onlineshoppingapi.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.domain.onlineshoppingapi.dtos.mapper.IShippingEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.param.ShippingParams;
import com.domain.onlineshoppingapi.dtos.response.ShippingResponse;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.exception.PaymentNotFoundException;
import com.domain.onlineshoppingapi.models.entity.Payment;
import com.domain.onlineshoppingapi.models.entity.Shipping;
import com.domain.onlineshoppingapi.models.repos.PaymentRepository;
import com.domain.onlineshoppingapi.models.repos.ShippingRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShippingService implements IShippingService{

    private final ShippingRepository shippingRepository;

    private final PaymentRepository paymentRepository;

    private final IShippingEntityResponseMapper shippingEntityMapper;

    public ShippingService(ShippingRepository shippingRepository, PaymentRepository paymentRepository, IShippingEntityResponseMapper shippingEntityMapper) {
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.shippingEntityMapper = shippingEntityMapper;
    }

    public ShippingResponse processShipping(ShippingParams shippingParams) {
        Long paymentId = shippingParams.getPaymentId();
        if(paymentId != null){
            Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
            Shipping shipping = new Shipping();
            shipping.setPayment(payment);
            shipping.setAddress(shippingParams.getAddress());
            shipping.setCode(shippingParams.getCode());
            shipping.setStatus(shippingParams.getStatus());
            shippingRepository.save(shipping);

            return shippingEntityMapper.shippingToShippingResponse(shipping);
        }

        throw new PaymentNotFoundException("Payment not found");
    }

    public Iterable<ShippingResponse> findAll() {
        Iterable<Shipping> shippings = shippingRepository.findAll();
        List<ShippingResponse> shippingResponses = new ArrayList<>();
        for (Shipping shipping : shippings) {
            shippingResponses.add(shippingEntityMapper.shippingToShippingResponse(shipping));
        }

        return shippingResponses;
    }

    public ShippingResponse findOne(Long id) {
        Shipping shipping = shippingRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return shippingEntityMapper.shippingToShippingResponse(shipping);
    }

    public ShippingResponse findByCode(SearchKeyParams searchKeyParams) {
        Shipping shipping = shippingRepository.findByCode(searchKeyParams.getSearchKey()).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return shippingEntityMapper.shippingToShippingResponse(shipping);
    }

    public Iterable<ShippingResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable) {
        Iterable<Shipping> shippings = shippingRepository.findByCodeContains(searchKeyParams.getSearchKey(), pageable);
        List<ShippingResponse> shippingResponses = new ArrayList<>();
        for (Shipping shipping : shippings) {
            shippingResponses.add(shippingEntityMapper.shippingToShippingResponse(shipping));
        }

        return shippingResponses;
    }

    public ShippingResponse update(Long id, ShippingParams shippingParams) {
        Shipping existingShipping = shippingRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Shipping not found"));
    
        Long paymentId = shippingParams.getPaymentId();
        if (paymentId != null) {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    
            existingShipping.setPayment(payment);
        }
    
        existingShipping.setAddress(shippingParams.getAddress());
        existingShipping.setCode(shippingParams.getCode());
        existingShipping.setStatus(shippingParams.getStatus());
        shippingRepository.save(existingShipping);
    
        return shippingEntityMapper.shippingToShippingResponse(existingShipping);
    }

    public ShippingResponse removeOne(Long id) {
        Shipping shipping = shippingRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Payment not found"));
        shippingRepository.delete(shipping);

        return shippingEntityMapper.shippingToShippingResponse(shipping);
    }
}

