package com.wishlist.core.entity.impl;

import com.wishlist.core.entity.Wishlist;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WishlistImpl implements Wishlist {

    private static final int LIMIT_PRODUCTS = 20;

    private final String name;
    private final String clientId;
    private final List<String> productIds;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public List<String> getProductIds() {
        return this.productIds;
    }

    @Override
    public boolean isFullWishlist() {
        if (this.productIds == null)
            return false;
        return this.productIds.size() >= LIMIT_PRODUCTS;
    }
}
