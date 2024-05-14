package com.wishlist.core.usecase;

import com.wishlist.core.response.FindProductResponseList;
import com.wishlist.core.usecase.interfaces.FindProductByNameInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.gateway.database.ProductDS;
import com.wishlist.gateway.database.model.ProductDataModel;
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
class FindProductByNameTest {

    FindProductByNameInput findProductByNameInput;

    @Mock
    private ProductDS productDS;

    @BeforeEach
    void init(){
        findProductByNameInput = new FindProductByName(productDS);
    }

    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio e que algum produto exista, deve retornar a lista de produtos com nome compátivel.")
    void deveConsultar(){
        List<ProductDataModel> produtos = List.of(
                new ProductDataModel("1", "nome 1"),
                new ProductDataModel("2", "nonome"),
                new ProductDataModel("3", "nomemo"));
        Mockito.when(productDS.findByNameLike("nome")).thenReturn(produtos);

        FindProductResponseList produtoResponseList = findProductByNameInput.findByName("nome");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(3, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio, mas não existe produto com o nome compátivel, " +
            "deve retornar a lista de produtos vazia, mas nao nula.")
    void deveConsultarERestornarVazio(){
        List<ProductDataModel> produtos = List.of();
        Mockito.when(productDS.findByNameLike("nome")).thenReturn(produtos);

        FindProductResponseList produtoResponseList = findProductByNameInput.findByName("nome");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(0, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é vazio e deve trazer todos os produtos da base.")
    void deveConsultarComNomeVazio(){
        List<ProductDataModel> produtos = List.of(
                new ProductDataModel("1", "nome 1"),
                new ProductDataModel("2", "aaaa"),
                new ProductDataModel("3", "bbbb"));
        Mockito.when(productDS.findByNameLike("")).thenReturn(produtos);

        FindProductResponseList produtoResponseList = findProductByNameInput.findByName("");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(3, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é nulo deve lançar uma exception.")
    void naoDeveConsultarComNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findProductByNameInput.findByName(null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("Nome não pode ser nulo.");
    }
}
