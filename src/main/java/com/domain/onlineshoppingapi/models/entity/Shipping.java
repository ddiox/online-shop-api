package com.domain.onlineshoppingapi.models.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "shippings_table")
public class Shipping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Code is required")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    @NotBlank(message = "Date is required")
    private LocalDate shippingDate;

    @NotBlank(message = "Address is required")
    private String address;

    @OneToOne
    private Payment payment;
}
