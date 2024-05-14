package com.wishlist.gateway.controller;

import com.wishlist.core.request.AddProductToWishlistRequest;
import com.wishlist.core.request.AddWishlistRequest;
import com.wishlist.core.response.*;
import com.wishlist.core.usecase.interfaces.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    private static final String WISH_LIST_ID = "1";
    private static final String CLIENT_ID = "1";
    private static final String PRODUCT_ID = "1";
    private static final String NAME = "name";

    @Mock
    private AddProductToWishlistInput addProductToWishlistInput;
    @Mock
    private AddWishlistInput addWishlistInput;
    @Mock
    private FindWishlistByIdInput findWishlistByIdInput;
    @Mock
    private FindProductFromWishlistInput findProductFromWishlistInput;
    @Mock
    private CheckProductExistsMyWishlistInput checkProductExistsMyWishlistInput;
    @Mock
    private RemoveProductFromWishlistInput removeProductFromWishlistInput;

    @InjectMocks
    private WishlistController wishlistController;

    @Test
    @DisplayName("Dado um produto que não esteja na wishlist, deve adicionar o produto na wishlist.")
    void deveAdicionarProdutoNaWishlist() {
        var responseMock = new AddProductWishlistResponse(WISH_LIST_ID, CLIENT_ID, Collections.EMPTY_LIST);
        when(addProductToWishlistInput.add(any())).thenReturn(responseMock);

        var request = new AddProductToWishlistRequest(WISH_LIST_ID, CLIENT_ID, PRODUCT_ID);
        var response = wishlistController.adicionarProdutoNaWishlist(request, "1");

        assertEquals(responseMock, response);
        verify(addProductToWishlistInput, times(1)).add(any());
    }

    @Test
    @DisplayName("Dado uma wishlist, deve adicionar esta nova wishlist nas wishlist cadastradas.")
    void deveAdicionarNovaWishlist() {
        var responseMock = new AddWishlistResponse(WISH_LIST_ID, NAME, CLIENT_ID);
        when(addWishlistInput.add(any(), any())).thenReturn(responseMock);

        var request = new AddWishlistRequest(NAME, CLIENT_ID);
        var response = wishlistController.adicionarNovaWishlist(request);

        assertEquals(responseMock, response);
        verify(addWishlistInput, times(1)).add(any(), any());
    }

    @Test
    @DisplayName("Dado um id de wishlish válido, deve consultar uma wishlist com esta id.")
    void deveConsultaWishlistPorId() {
        var responseMock = new FindWishlistResponse(WISH_LIST_ID, NAME, CLIENT_ID, Collections.EMPTY_LIST);
        when(findWishlistByIdInput.findById(any())).thenReturn(responseMock);

        var response = wishlistController.consultaWishlistPorId(WISH_LIST_ID);

        assertEquals(responseMock, response);
        verify(findWishlistByIdInput, times(1)).findById(any());
    }

    @Test
    @DisplayName("Dado produtos listados numa wishlist, deve consultar todos os produtos cadastrados na wishlist.")
    void deveConsultaProdutosPorWishlist() {
        var responseMock = new FindProductsByWishlistResponse(WISH_LIST_ID, NAME);
        when(findProductFromWishlistInput.findProductsByWishlist(any())).thenReturn(responseMock);

        var response = wishlistController.consultaProdutosPorWishlist(WISH_LIST_ID);

        assertEquals(responseMock, response);
        verify(findProductFromWishlistInput, times(1)).findProductsByWishlist(any());
    }

    @Test
    @DisplayName("Dado um produto e uma wshlist, deve verificar se este produto está incluído na wishlist informada.")
    void deveverificarProdutosExisteNaWishlist() {
        var responseMock = new ProductExistsMyWishlistResponse(FALSE);
        when(checkProductExistsMyWishlistInput.checkProductExistsMyWishlist(any(), any())).thenReturn(responseMock);

        var response = wishlistController.verificaProdutosExisteNaWishlist(WISH_LIST_ID, PRODUCT_ID);

        assertEquals(responseMock, response);
        verify(checkProductExistsMyWishlistInput, times(1)).checkProductExistsMyWishlist(any(), any());
    }

    @Test
    @DisplayName("Dado um produto na wishlist, deve remover o produto informado da wishlist.")
    void deveRemoverProdutoDaWishlist() {
        doNothing().when(removeProductFromWishlistInput).removeProductFromWishlistById(any(), any());

        wishlistController.removerProdutoDaWishlist(WISH_LIST_ID, PRODUCT_ID);

        verify(checkProductExistsMyWishlistInput, times(0)).checkProductExistsMyWishlist(any(), any());
    }

}