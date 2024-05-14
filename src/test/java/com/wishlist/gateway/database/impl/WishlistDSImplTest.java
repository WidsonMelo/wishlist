package com.wishlist.gateway.database.impl;

import com.wishlist.gateway.database.model.WishlistDataModel;
import com.wishlist.gateway.database.repository.WishlistRepository;
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
class WishlistDSImplTest {

    private static final String WISH_LIST_ID = "1";
    private static final String CLIENT_ID = "1";
    private static final String PRODUCT_ID = "1";
    private static final String NAME = "name";

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistDSImpl wishlistDS;

    @Test
    @DisplayName("Dado um wishlistId e um clientId, deve buscar alguma wishlist com o clientId informado.")
    void findByIdAndClientId() {
        var responseMock = new WishlistDataModel(WISH_LIST_ID, NAME, CLIENT_ID, List.of(PRODUCT_ID));

        when(wishlistRepository.findByIdAndClientId(any(), any())).thenReturn(Optional.of(responseMock));
        var response = wishlistDS.findWishlistByIdAndClientid(WISH_LIST_ID, CLIENT_ID);

        assertEquals(WISH_LIST_ID, response.get().getId());
        assertEquals(NAME, response.get().getName());
        verify(wishlistRepository, times(1)).findByIdAndClientId(any(), any());
    }

    @Test
    @DisplayName("Dado um wishlistId, deve buscar i wishlist com o id informado.")
    void findWishlistById() {
        var responseMock = new WishlistDataModel(WISH_LIST_ID, NAME, CLIENT_ID, List.of(PRODUCT_ID));

        when(wishlistRepository.findById(any())).thenReturn(Optional.of(responseMock));
        var response = wishlistDS.findWishlistById(WISH_LIST_ID);

        assertEquals(WISH_LIST_ID, response.get ().getId());
        assertEquals(NAME, response.get().getName());
        verify(wishlistRepository, times(1)).findById(any());
    }
}