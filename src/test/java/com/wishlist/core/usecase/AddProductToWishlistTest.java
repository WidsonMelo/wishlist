package com.wishlist.core.usecase;

import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import com.wishlist.core.request.AddProductToWishlistRequest;
import com.wishlist.core.response.AddProductWishlistResponse;
import com.wishlist.core.usecase.interfaces.AddProductToWishlistInput;
import com.wishlist.exception.InvalidOperationException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class AddProductToWishlistTest {

    AddProductToWishlistInput addProductToWishlist;

    @Mock
    private WishlistDS wishlistDS;
    @Mock
    private CheckProductExists checkProductExists;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @BeforeEach
    void init(){
        addProductToWishlist = new AddProductToWishlist(wishlistDS, checkProductExists, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que wishlist, cliente e o produto existam, " +
            "e a wishlis não esteja cheia, deve adicionar o produto a whishlist")
    void deveAdicionarProdutoAWishlist(){
        AddProductToWishlistRequest wishlistRequest = new AddProductToWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getClientId(), produtos);

        Mockito.when(wishlistDS.findWishlistByIdAndClientid(wishlistRequest.getId(),
                wishlistRequest.getClientId())).thenReturn(Optional.of(wishlistDataModel));

        Mockito.when(checkProductExists.checkProductExistsById("3")).thenReturn(true);

        AddProductWishlistResponse wishlistResponse = addProductToWishlist.add(wishlistRequest);

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertNotNull(wishlistResponse.getData().getProductIds());
        Assertions.assertEquals(3, wishlistResponse.getData().getProductIds().size());
    }
    @Test
    @DisplayName("Dado que a wishlist não existe, nenhum produto deve ser adicionado, e deve lançar exception.")
    void naoDeveAdicionarProdutoAWishlistQuandoNaoExisteWishlist(){
        AddProductToWishlistRequest wishlistRequest = new AddProductToWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getClientId(), produtos);

        Mockito.when(wishlistDS.findWishlistByIdAndClientid(wishlistRequest.getId(),
                wishlistRequest.getClientId())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addProductToWishlist.add(wishlistRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Wishlist não existe.");
    }
    @Test
    @DisplayName("Dado que o produto não existe, deve lançar exception.")
    void naoDeveAdicionarSeProdutoNaoExiste(){
        AddProductToWishlistRequest wishlistRequest = new AddProductToWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getClientId(), produtos);

        Mockito.when(wishlistDS.findWishlistByIdAndClientid(wishlistRequest.getId(),
                wishlistRequest.getClientId())).thenReturn(Optional.of(wishlistDataModel));
        Mockito.when(checkProductExists.checkProductExistsById("3")).thenReturn(false);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addProductToWishlist.add(wishlistRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não existe.");
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto existam, e que a wishlist possui 20 produtos, " +
            "não deve adicionar mais um produto, e deve lançar exception.")
    void naoDeveAdicionarOutroProdutoComWishlistCheia(){
        AddProductToWishlistRequest wishlistRequest = new AddProductToWishlistRequest("1","2", "21");
        List<String> produtos = IntStream.range(0, 20)
                .mapToObj(String::valueOf).collect(Collectors.toList());
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getClientId(), produtos);

        Mockito.when(wishlistDS.findWishlistByIdAndClientid(wishlistRequest.getId(),
                wishlistRequest.getClientId())).thenReturn(Optional.of(wishlistDataModel));
        Mockito.when(checkProductExists.checkProductExistsById("21")).thenReturn(true);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> addProductToWishlist.add(wishlistRequest))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Wishlist já está cheia.");
    }
}
