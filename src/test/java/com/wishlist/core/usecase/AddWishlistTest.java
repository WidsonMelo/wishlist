package com.wishlist.core.usecase;

import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import com.wishlist.core.response.AddWishlistResponse;
import com.wishlist.core.usecase.interfaces.AddWishlistInput;
import com.wishlist.core.usecase.interfaces.CheckClientExistsInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import com.wishlist.gateway.database.model.WishlistDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddWishlistTest {

    AddWishlistInput addWishlist;

    @Mock
    private WishlistDS wishlistDS;

    @Mock
    private CheckClientExistsInput checkClientExists;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @BeforeEach
    void init(){
        addWishlist = new AddWishlist(wishlistDS, checkClientExists, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que o nome e o id do cliente não sejam nulos, vazios ou em branco, e que o cliente exista, " +
            "deve adicionar uma nova wishlist.")
    void deveAdicionarNovaWishlist(){
        WishlistDataModel wishlistDataModel = new WishlistDataModel("1","lista", "1", List.of());

        Mockito.when(wishlistDS.save(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(wishlistDataModel);
        Mockito.when(checkClientExists.checkClientExistsById("1")).thenReturn(true);

        AddWishlistResponse wishlistResponse = addWishlist.add("lista", "1");

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(wishlistResponse);
    }
    @Test
    @DisplayName("Dado que o nome passado é nulo, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addWishlist.add(null, "1"))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o nome passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoNomeEmBranco(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addWishlist.add("  ", "1"))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente passado é nulo, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addWishlist.add("lista", null))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteEmBranco(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addWishlist.add("lista", " "))
                .isInstanceOf(MissingDataException.class)
                .hasMessage("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente não existe passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteNaoExiste(){
        Mockito.when(checkClientExists.checkClientExistsById("1")).thenReturn(false);
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addWishlist.add("lista", "1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cliente não encontrado.");
    }
}
