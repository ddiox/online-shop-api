package com.domain.onlineshoppingapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.domain.onlineshoppingapi.controllers.ProductController;
import com.domain.onlineshoppingapi.dtos.mapper.IProductEntityResponseMapper;
import com.domain.onlineshoppingapi.dtos.mapper.IProductRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.mapper.ISearchKeyRequestParamsMapper;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;
import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;
import com.domain.onlineshoppingapi.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private IProductEntityResponseMapper productEntityMapper;

    @Mock
    private IProductRequestParamsMapper productRequestMapper;

    @Mock
    private ISearchKeyRequestParamsMapper searchKeyRequestMapper;

    @Test
    public void ProductController_CreateProduct_ReturnsCreated() throws Exception {
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

        ProductResponse productResponse = ProductResponse.builder()
            .code("P001")
            .name("Product 1")
            .description("Product 1 description")
            .price(100.00)
            .build();

        when(productEntityMapper.productToProductResponse(product)).thenReturn(productResponse);
        when(productService.saveOrUpdate(Mockito.any(ProductParams.class))).thenReturn(productResponse);

    // Perform the request and assert the response
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(productParams)));

    // You can add more assertions based on your specific use case
    }
    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}