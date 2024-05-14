package com.wishlist.gateway.database.impl;

import com.wishlist.gateway.database.model.ProductDataModel;
import com.wishlist.gateway.database.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDSImplTest {

    private static final String PRODUCT_ID = "1";
    private static final String NAME = "name";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductDSImpl productDS;

    @Test
    @DisplayName("Dado um id de product, deve buscar o product pelo id informado.")
    void findById() {
        var responseMock = new ProductDataModel(PRODUCT_ID, NAME);
        when(productRepository.findById(any())).thenReturn(Optional.of(responseMock));

        var response = productDS.findById(PRODUCT_ID);

        assertEquals(PRODUCT_ID, response.get().getId());
        assertEquals(NAME, response.get().getName());
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Dado uma lista de ids de produtos, deve buscar todos os produtos com os ids informados.")
    void findByIdIn() {
        var responseMock = List.of(new ProductDataModel(PRODUCT_ID, NAME));
        when(productRepository.findAllById(List.of(PRODUCT_ID))).thenReturn(responseMock);

        var response = productDS.findByIdIn(List.of(PRODUCT_ID));

        assertEquals(PRODUCT_ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        verify(productRepository, times(1)).findAllById(List.of(PRODUCT_ID));
    }

    @Test
    @DisplayName("Dado um nome de product, deve buscar os products que contém o nome informado.")
    void findByNameLike() {
        var responseMock = List.of(new ProductDataModel(PRODUCT_ID, NAME));
        when(productRepository.findByNameLike(NAME)).thenReturn(responseMock);

        var response = productDS.findByNameLike(NAME);

        assertEquals(PRODUCT_ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        verify(productRepository, times(1)).findByNameLike(NAME);
    }
}