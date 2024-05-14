package com.wishlist.core.usecase;

import com.wishlist.gateway.database.ProductDS;
import com.wishlist.gateway.database.model.ProductDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CheckProductExistsTest {

    @InjectMocks
    private CheckProductExists checkProductExists;

    @Mock
    private ProductDS productDS;

    @Test
    @DisplayName("Dado que o produto existe deve retornar true.")
    void deveRetornarTrueSeExisteProduto(){

        Mockito.when(productDS.findById("1")).thenReturn(Optional.of(new ProductDataModel("1", "produto")));

        Assertions.assertTrue(checkProductExists.checkProductExistsById("1"));
    }
    @Test
    @DisplayName("Dado que o nao produto existe deve retornar false.")
    void deveRetornarFalseSeNaoExisteProduto(){

        Mockito.when(productDS.findById("1")).thenReturn(Optional.empty());

        Assertions.assertFalse(checkProductExists.checkProductExistsById("1"));
    }
    @Test
    @DisplayName("Dado que o parametro id passado sejua nullo, deve retornar false.")
    void deveRetornarFalseQuandoPassadoNull(){
        Assertions.assertFalse(checkProductExists.checkProductExistsById(null));
    }
}
