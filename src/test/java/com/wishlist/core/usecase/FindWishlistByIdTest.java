package com.wishlist.core.usecase;

import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindWishlistByIdInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import com.wishlist.gateway.database.model.WishlistDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FindWishlistByIdTest {

    FindWishlistByIdInput findWishlistByIdInput;

    @Mock
    private WishlistDS wishlistDS;

    @BeforeEach
    void init(){
        findWishlistByIdInput = new FindWishlistById(wishlistDS);
    }

    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que a wishlist com a id exista, deve retornar a wishlist.")
    void deveConsultar(){
        Mockito.when(wishlistDS.findWishlistById("1"))
                .thenReturn(Optional.of(new WishlistDataModel("1", "nome 1", "1", List.of("1"))));

        FindWishlistResponse wishlistResponse = findWishlistByIdInput.findById("1");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertNotNull(wishlistResponse.getData());
        Assertions.assertEquals("1", wishlistResponse.getData().getId());
    }
    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que a wishlist com a id não exista, deve lançar exception.")
    void deveLancarExceptionSeWishlistNaoExiste(){
        Mockito.when(wishlistDS.findWishlistById("1")).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findWishlistByIdInput.findById("1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Wishlist não encontrada.");
    }
    @Test
    @DisplayName("Dado que a id é nula , deve lançar exception.")
    void deveLancarExceptionParaIdNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> findWishlistByIdInput.findById(null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("Id deve ser preenchida.");
    }
}
