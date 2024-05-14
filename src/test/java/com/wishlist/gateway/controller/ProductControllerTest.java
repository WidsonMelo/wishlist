package com.wishlist.gateway.controller;

import com.wishlist.core.response.FindProductResponseList;
import com.wishlist.core.usecase.interfaces.FindProductByNameInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private static final String PRODUCT_ID = "1";
    private static final String NAME = "name";
    @Mock
    private FindProductByNameInput findProductByNameInput;

    @InjectMocks
    private ProductController productController;

    @Test
    @DisplayName("Dado um nome de produto, deve consultar os produtos que contenha o nome informado.")
    void consultaProdutoPorNome() {
        var responseMock = new FindProductResponseList(PRODUCT_ID, NAME);

        when(findProductByNameInput.findByName(any())).thenReturn(responseMock);
        var response = productController.consultaProdutoPorNome(NAME);

        assertEquals(PRODUCT_ID, response.getData().get(0).getId());
        assertEquals(NAME, response.getData().get(0).getName());
        verify(findProductByNameInput, times(1)).findByName(any());
    }
}