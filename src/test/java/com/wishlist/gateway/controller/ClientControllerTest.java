package com.wishlist.gateway.controller;

import com.wishlist.core.response.FindClientResponseList;
import com.wishlist.core.usecase.interfaces.FindClientByNameInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private static final String CLIENT_ID = "1";
    private static final String NAME = "name";


    @Mock
    private FindClientByNameInput findClientByNameInput;

    @InjectMocks
    private ClientController clientController;

    @Test
    @DisplayName("Dado um nome de client, deve consultar os clients que contém o link do informado.")
    void consultaClientePorNome() {
        var responseMock = new FindClientResponseList(CLIENT_ID, NAME);
        when(findClientByNameInput.findByName(NAME)).thenReturn(responseMock);

        var response = clientController.consultaClientePorNome(NAME);

        assertEquals(responseMock, response);
        verify(findClientByNameInput, times(1)).findByName(NAME);
    }

    @Test
    @DisplayName("Dado uma lista de clientes cadastrados, deve consultar todos os clientes cadastrados.")
    void consultaClientes() {
        var responseMock = new FindClientResponseList(CLIENT_ID, NAME);
        when(findClientByNameInput.findAll()).thenReturn(responseMock);

        var response = clientController.consultaClientes();

        assertEquals(responseMock, response);
        verify(findClientByNameInput, times(1)).findAll();
    }
}