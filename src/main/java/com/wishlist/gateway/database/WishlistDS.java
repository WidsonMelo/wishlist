package com.wishlist.gateway.database;


import com.wishlist.core.entity.Wishlist;
import com.wishlist.gateway.database.model.WishlistDataModel;

import java.util.Optional;

public interface WishlistDS {
    Optional<WishlistDataModel> findWishlistByIdAndClientid(String id, String clientId);
    Optional<WishlistDataModel> findWishlistById(String id);
    WishlistDataModel save(String idWishlist, Wishlist wishlist);
}
