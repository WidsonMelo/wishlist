package com.wishlist.gateway.database.impl;

import com.wishlist.gateway.database.model.ClientDataModel;
import com.wishlist.gateway.database.model.ProductDataModel;
import com.wishlist.gateway.database.repository.ClientRepository;
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
class ClientDSImplTest {

    private static final String CLIENT_ID = "1";
    private static final String NAME = "name";

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientDSImpl clientDS;

    @Test
    @DisplayName("Dado um id de client, deve buscar o cliente pelo id informado.")
    void findById() {
        var responseMock = new ClientDataModel(CLIENT_ID, NAME);
        when(clientRepository.findById(any())).thenReturn(Optional.of(responseMock));

        var response = clientDS.findById(CLIENT_ID);

        assertEquals(CLIENT_ID, response.get().getId());
        assertEquals(NAME, response.get().getName());
        verify(clientRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Dado um nome de client, deve buscar os clientes que contem o nome informado.")
    void findByNameLike() {
        var responseMock = List.of(new ClientDataModel(CLIENT_ID, NAME));
        when(clientRepository.findByNameLike(NAME)).thenReturn(responseMock);

        var response = clientDS.findByNameLike(NAME);

        assertEquals(CLIENT_ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        verify(clientRepository, times(1)).findByNameLike(NAME);
    }

    @Test
    @DisplayName("Dado uma lista de clientes, deve buscar todos os clientes cadastrados.")
    void findAll() {
        var responseMock = List.of(new ClientDataModel(CLIENT_ID, NAME));
        when(clientRepository.findAll()).thenReturn(responseMock);

        var response = clientDS.findAll();

        assertEquals(CLIENT_ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }
}