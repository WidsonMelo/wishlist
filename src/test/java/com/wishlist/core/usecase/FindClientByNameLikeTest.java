package com.wishlist.core.usecase;

import com.wishlist.core.response.FindClientResponseList;
import com.wishlist.core.usecase.interfaces.FindClientByNameInput;
import com.wishlist.exception.MissingDataException;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FindClientByNameLikeTest {

    FindClientByNameInput findClientByNameInput;

    @Mock
    private ClientDS clientDS;

    @BeforeEach
    void init(){
        findClientByNameInput = new FindClientByName(clientDS);
    }

    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio e que algum cliente exista, deve retornar a lista de cliente com nome compátivel.")
    void deveConsultar(){
        List<ClientDataModel> cliente = List.of(
                new ClientDataModel("1", "nome 1"),
                new ClientDataModel("2", "nonome"),
                new ClientDataModel("3", "nomemo"));
        Mockito.when(clientDS.findByNameLike("nome")).thenReturn(cliente);

        FindClientResponseList clienteResponseList = findClientByNameInput.findByName("nome");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(3, clienteResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio, mas não existe cliente com o nome compátivel, " +
            "deve retornar a lista de clientes vazia, mas nao nula.")
    void deveConsultarERestornarVazio(){
        List<ClientDataModel> cliente = List.of();
        Mockito.when(clientDS.findByNameLike("nome")).thenReturn(cliente);

        FindClientResponseList clienteResponseList = findClientByNameInput.findByName("nome");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(0, clienteResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é vazio e deve trazer todos os clientes da base.")
    void deveConsultarComNomeVazio(){
        List<ClientDataModel> cliente = List.of(
                new ClientDataModel("1", "nome 1"),
                new ClientDataModel("2", "aaaa"),
                new ClientDataModel("3", "bbbb"));
        Mockito.when(clientDS.findByNameLike("")).thenReturn(cliente);

        FindClientResponseList clienteResponseList = findClientByNameInput.findByName("");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(3, clienteResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é nulo deve lançar uma exception.")
    void naoDeveConsultarComNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findClientByNameInput.findByName(null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("Nome não pode ser nulo.");
    }
}
