package com.domain.onlineshoppingapi.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.domain.onlineshoppingapi.models.entity.Product;
import com.domain.onlineshoppingapi.models.repos.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {
    
        @Autowired
        private ProductRepository productRepository;

        @Test
        public void ProductRepository_SaveAll_ReturnsSavedProduct() {
            //Arrange
            Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
            //Act
            Product savedProduct = productRepository.save(product);

            //Assertion
            Assertions.assertNotNull(savedProduct);
            Assertions.assertTrue(savedProduct.getId() > 0); 
        }

        @Test
        public void ProductRepository_GetAll_ReturnMoreThanOneProduct() {
            //Arrange
            Product product1 = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();

            Product product2 = Product.builder()
                .code("P002")
                .name("Product 2")
                .description("Product 2 description")
                .price(200.00)
                .build();
    
            //Act
            productRepository.save(product1);
            productRepository.save(product2);

            List<Product> productList = productRepository.findAll();

            //Assertion
            Assertions.assertNotNull(productList);
            Assertions.assertTrue(productRepository.findAll().size() > 1); 
        }

        @Test
        public void ProductRepository_GetById_ReturnsProduct() {
            //Arrange
            Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
            //Act
            productRepository.save(product);

            Product foundProduct = productRepository.findById(product.getId()).get();

            //Assertion
            Assertions.assertNotNull(foundProduct);
            Assertions.assertTrue(foundProduct.getId() > 0); 
        }

        @Test
        public void ProductRepository_FindByCode_ReturnsNotNull() {
            //Arrange
            Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
            //Act
            productRepository.save(product);

            Optional<Product> foundProductOptional = productRepository.findByCode(product.getCode());

            //Assertion
            Assertions.assertNotNull(foundProductOptional);
            Assertions.assertTrue(foundProductOptional.isPresent());
        }

        @Test
        public void PokemonRepository_UpdatePokemon_ReturnsPokemonNotNull(){
            //Arrange
            Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
            //Act
            productRepository.save(product);

            Product productSave = productRepository.findById(product.getId()).get();
            productSave.setName("Product 1 Updated");
            productSave.setDescription("Product 1 description Updated");
            productSave.setPrice(200.00);

            Product updatedProduct = productRepository.save(productSave);
        

            Assertions.assertNotNull(updatedProduct);
            Assertions.assertNotNull(updatedProduct.getName());
            Assertions.assertNotNull(updatedProduct.getDescription());
            Assertions.assertNotNull(updatedProduct.getPrice());
            Assertions.assertTrue(updatedProduct.getId() > 0); 
        }

        @Test
        public void ProductRepository_ProductDelete_ReturnsProductEmpty(){
            //Arrange
            Product product = Product.builder()
                .code("P001")
                .name("Product 1")
                .description("Product 1 description")
                .price(100.00)
                .build();
    
            //Act
            productRepository.save(product);

            productRepository.deleteById(product.getId());

            Optional<Product> deletedProduct = productRepository.findById(product.getId());

            //Assertion
            Assertions.assertTrue(deletedProduct.isEmpty());
        }
}
