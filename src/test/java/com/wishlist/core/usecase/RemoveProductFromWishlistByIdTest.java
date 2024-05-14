package com.wishlist.core.usecase;

import com.wishlist.core.entity.Wishlist;
import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.RemoveProductFromWishlistInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RemoveProductFromWishlistByIdTest {

    RemoveProductFromWishlistInput removeProductFromWishlistInput;

    @Mock
    private WishlistDS wishlistDS;
    @Mock
    private FindWishlistById findWishlistById;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @Captor
    private ArgumentCaptor<Wishlist> wishlistArgumentCaptor;

    @BeforeEach
    void init(){
        removeProductFromWishlistInput = new RemoveProductFromWishlistById(wishlistDS, findWishlistById, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto passados não são nulos, e que a wishlist existe e tem o produto passado," +
            " deve remover o produto passado da wishlist.")
    void deveRemoverProdutoDaWishlist(){
        List<String> produtos = new ArrayList<>();
        produtos.add("1");
        produtos.add("2");
        var wishlistResponse = new FindWishlistResponse("1", "nome", "1", produtos);
        Mockito.when(findWishlistById.findById("1")).thenReturn(wishlistResponse);

        removeProductFromWishlistInput.removeProductFromWishlistById("1", "1");

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), wishlistArgumentCaptor.capture());
        Wishlist wishlistSalva = wishlistArgumentCaptor.getValue();
        Assertions.assertNotNull(wishlistSalva);
        Assertions.assertNotNull(wishlistSalva.getProductIds());
        Assertions.assertEquals(1, wishlistSalva.getProductIds().size());
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto passados não são nulos, e que a wishlist existe e não tem o produto passado," +
            " deve lançar exception.")
    void deveLancarExceptionSeWishlistNaoTemProdutos(){
        var wishlistResponse = new FindWishlistResponse("1", "nome", "1", List.of());
        Mockito.when(findWishlistById.findById("1")).thenReturn(wishlistResponse);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removeProductFromWishlistInput.removeProductFromWishlistById("1", "1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrado na wishlist.");
    }
    @Test
    @DisplayName("Dado que o produto passado é nulo, deve lançar uma exception.")
    void deveLancarExceptionParaProdutoNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removeProductFromWishlistInput.removeProductFromWishlistById("1", null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id do produto.");
    }
    @Test
    @DisplayName("Dado que a wishlist passado é nula, deve lançar uma exception.")
    void deveLancarExceptionParaWishlistNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removeProductFromWishlistInput.removeProductFromWishlistById(null, "1"))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
}
