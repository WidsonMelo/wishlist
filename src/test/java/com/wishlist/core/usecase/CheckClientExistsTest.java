package com.wishlist.core.usecase;

import com.wishlist.core.usecase.interfaces.CheckClientExistsInput;
import com.wishlist.gateway.database.ClientDS;
import com.wishlist.gateway.database.model.ClientDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CheckClientExistsTest {

    CheckClientExistsInput checkClientExistsInput;

    @Mock
    private ClientDS clientDS;

    @BeforeEach
    void init(){
        checkClientExistsInput = new CheckClientExists(clientDS);
    }

    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que o cliente com a id exista, deve retornar true.")
    void deveConsultar(){
        Mockito.when(clientDS.findById("1")).thenReturn(Optional.of(new ClientDataModel("1", "nome 1")));

        boolean retorno = checkClientExistsInput.checkClientExistsById("1");

        Assertions.assertTrue(retorno);
    }
    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que o cliente com a id não exista, deve retornar true.")
    void deveConsultarERetornaFalse(){
        Mockito.when(clientDS.findById("1")).thenReturn(Optional.empty());
        boolean retorno = checkClientExistsInput.checkClientExistsById("1");

        Assertions.assertFalse(retorno);
    }
    @Test
    @DisplayName("Dado que a id é nula , deve retornar false.")
    void deveConsultarERetornaFalseQuandoIdNula(){
        boolean retorno = checkClientExistsInput.checkClientExistsById(null);

        Assertions.assertFalse(retorno);
    }
}
