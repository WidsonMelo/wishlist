package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.ProductExistsMyWishlistResponse;

public interface CheckProductExistsMyWishlistInput {

    ProductExistsMyWishlistResponse checkProductExistsMyWishlist(String wishlistId, String productId);
}
