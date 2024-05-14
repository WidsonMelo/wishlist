package com.wishlist.core.usecase;

import com.wishlist.core.response.FindProductsByWishlistResponse;
import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindProductFromWishlistInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FindProductFromWishlistTest {

    FindProductFromWishlistInput findProductFromWishlistInput;

    @Mock
    private ProductDS productDS;

    @Mock
    private FindWishlistById findWishlistById;

    @BeforeEach
    void init(){
        findProductFromWishlistInput = new FindProductFromWishlist(productDS, findWishlistById);
    }

    @Test
    @DisplayName("Dado que id não é nulo ou vazio, que a wishlist exista e que exista produtos na wishlist, " +
            "deve retornar uma lista de produtos que existem na wishlist")
    void deveConsultar(){
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        FindWishlistResponse wishlistResponse = new FindWishlistResponse("1","nome", "1", produtos);

        List<ProductDataModel> productDataModelList = List.of(
                new ProductDataModel("4", "nome 4"),
                new ProductDataModel("5", "nome 5")
        );

        Mockito.when(findWishlistById.findById("1")).thenReturn(wishlistResponse);
        Mockito.when(productDS.findByIdIn(produtos)).thenReturn(productDataModelList);

        FindProductsByWishlistResponse consultaProdutosPorWishlist = findProductFromWishlistInput.findProductsByWishlist("1");

        Assertions.assertNotNull(consultaProdutosPorWishlist);
        Assertions.assertNotNull(consultaProdutosPorWishlist.getData());
        Assertions.assertEquals(2, consultaProdutosPorWishlist.getData().size());
    }
    @Test
    @DisplayName("Dado que o não existem produtos na wishlist, deve lançar exception.")
    void naoConsultarELancaExceptionQuandoWishlistNaoTiverProdutos(){
        FindWishlistResponse wishlistResponse = new FindWishlistResponse("1","nome", "1", List.of());

        Mockito.when(findWishlistById.findById("1")).thenReturn(wishlistResponse);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findProductFromWishlistInput.findProductsByWishlist("1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("A wishlist não possui produtos.");
    }
    @Test
    @DisplayName("Dado que a id é nula, e deve lançar exception.")
    void naoDeveConsultarELancaExceptionParaIdNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findProductFromWishlistInput.findProductsByWishlist(null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
    @Test
    @DisplayName("Dado que a id é vazia, e deve lançar exception.")
    void naoDeveConsultarELancaExceptionParaIdVazia(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findProductFromWishlistInput.findProductsByWishlist(""))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
}
