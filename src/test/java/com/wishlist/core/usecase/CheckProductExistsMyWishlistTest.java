package com.wishlist.core.usecase;

import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.response.ProductExistsMyWishlistResponse;
import com.wishlist.core.usecase.interfaces.CheckProductExistsMyWishlistInput;
import com.wishlist.exception.MissingDataException;
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
class CheckProductExistsMyWishlistTest {

    CheckProductExistsMyWishlistInput checkProductExistsMyWishlistInput;

    @Mock
    private FindWishlistById findWishlistById;

    @BeforeEach
    void init(){
        checkProductExistsMyWishlistInput = new CheckProductExistsMyWishlist(findWishlistById);
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista com o produto, " +
            "deve retornar true.")
    void deveRetornarTrueSeExisteOProdutoNaWishlist(){
        var consultarWishlistResponse = new FindWishlistResponse("1", "nome", "1", List.of("2"));
        Mockito.when(findWishlistById.findById("1")).thenReturn(consultarWishlistResponse);

        ProductExistsMyWishlistResponse wishlistResponse = checkProductExistsMyWishlistInput
                .checkProductExistsMyWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertTrue(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista mas sem o produto passado, " +
            "deve retornar false.")
    void deveRetornarFalseSeNaoExisteOProdutoNaWishlist(){
        var consultarWishlistResponse = new FindWishlistResponse("1", "nome", "1", List.of("3"));
        Mockito.when(findWishlistById.findById("1")).thenReturn(consultarWishlistResponse);

        ProductExistsMyWishlistResponse wishlistResponse = checkProductExistsMyWishlistInput
                .checkProductExistsMyWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertFalse(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista mas sem nenhum produto, " +
            "deve retornar false.")
    void deveRetornarFalseSeWishlistNaoTemProdutos(){
        var consultarWishlistResponse = new FindWishlistResponse("1", "nome", "1", List.of());
        Mockito.when(findWishlistById.findById("1")).thenReturn(consultarWishlistResponse);

        ProductExistsMyWishlistResponse wishlistResponse = checkProductExistsMyWishlistInput
                .checkProductExistsMyWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertFalse(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist passada é nula e o produto não é nulo ou vazio, deve lançar exception.")
    void deveLancarExceptionSeWishlistNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> checkProductExistsMyWishlistInput.checkProductExistsMyWishlist(null, "2"))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
    @Test
    @DisplayName("Dado que a wishlist não é nula ou vazia, mas que o produto passada é nulo, deve lançar exception.")
    void deveLancarExceptionSeProdutoNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> checkProductExistsMyWishlistInput.checkProductExistsMyWishlist("1", null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id do produto.");
    }
}
