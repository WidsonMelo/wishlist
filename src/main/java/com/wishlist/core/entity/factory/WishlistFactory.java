package com.wishlist.core.entity.factory;

import com.wishlist.core.entity.Wishlist;

import java.util.List;

public interface WishlistFactory {

    Wishlist create(String name, String clientId, List<String> productIds);
}
