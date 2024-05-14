package com.wishlist.core.usecase.interfaces;

public interface RemoveProductFromWishlistInput {
    void removeProductFromWishlistById(String wishlistId, String productId);
}
