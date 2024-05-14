package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.FindProductsByWishlistResponse;

public interface FindProductFromWishlistInput {

    FindProductsByWishlistResponse findProductsByWishlist(String id);
}
