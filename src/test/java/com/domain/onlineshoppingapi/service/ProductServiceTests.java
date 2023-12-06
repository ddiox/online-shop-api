package com.domain.onlineshoppingapi.service;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.onlineshoppingapi.dtos.mapper.IProductEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;
import com.domain.onlineshoppingapi.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private IProductEntityResponseMapper productEntityMapper;

    
    @Test
    public void ProductService_CreateProduct_ReturnsProductDto() {
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        ProductParams productParams = ProductParams.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        // Act
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        when(productEntityMapper.productToProductResponse(product)).thenReturn(ProductResponse.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build());
    
        ProductResponse productResponse = productService.saveOrUpdate(productParams);
    
        // Assertion
        Assertions.assertNotNull(productResponse);
    }

    @Test
    public void ProductService_GetAllProducts_ReturnsResponseDto(){
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        // Act
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productEntityMapper.productToProductResponse(product)).thenReturn(ProductResponse.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build());
    
        Iterable<ProductResponse> productResponseList = productService.findAll();
    
        // Assertion
        Assertions.assertNotNull(productResponseList);
    }

    @Test
    public void ProductService_GetById_ReturnsResponseDto(){
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        // Act
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productEntityMapper.productToProductResponse(product)).thenReturn(ProductResponse.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build());
    
        ProductResponse productResponse = productService.findOne(1L);
    
        // Assertion
        Assertions.assertNotNull(productResponse);
    }

    @Test
    public void ProductService_Update_ReturnsResponseDto(){
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        ProductParams productParams = ProductParams.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        // Act
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        when(productEntityMapper.productToProductResponse(product)).thenReturn(ProductResponse.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build());
    
        ProductResponse productResponse = productService.update(1L, productParams);
    
        // Assertion
        Assertions.assertNotNull(productResponse);
    }

    @Test
    public void ProductService_Delete_ReturnsResponseDto(){
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
        // Act
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        productService.removeOne(1L);
    
        // Assertion
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}
