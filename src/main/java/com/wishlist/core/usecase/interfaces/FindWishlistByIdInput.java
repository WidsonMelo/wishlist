package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.FindWishlistResponse;

public interface FindWishlistByIdInput {
    FindWishlistResponse findById(String id);
}
