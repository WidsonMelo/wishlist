package com.wishlist.core.entity.factory;

import com.wishlist.core.entity.Wishlist;
import com.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class WishlistFactoryTest {

    WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @Test
    @DisplayName("Dado o wishlistFactory, deve criar uma entity.")
    void deveCriarEnity(){
        Wishlist wishlist = wishlistFactory.create("List", "1", List.of("2", "3"));

        Assertions.assertNotNull(wishlist);
        Assertions.assertEquals("List", wishlist.getName());
        Assertions.assertEquals("1", wishlist.getClientId());
        Assertions.assertNotNull(wishlist.getProductIds());
        Assertions.assertEquals(2, wishlist.getProductIds().size());
        org.assertj.core.api.Assertions.assertThat(wishlist.getProductIds()).contains("2", "3");
    }
}
