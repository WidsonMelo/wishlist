package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.request.AddProductToWishlistRequest;
import com.wishlist.core.response.AddProductWishlistResponse;

public interface AddProductToWishlistInput {

    AddProductWishlistResponse add(AddProductToWishlistRequest wishlistRequest);
}
