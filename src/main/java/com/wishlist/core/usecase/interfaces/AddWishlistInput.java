package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.AddWishlistResponse;

public interface AddWishlistInput {

    AddWishlistResponse add(String name, String clientId);
}
