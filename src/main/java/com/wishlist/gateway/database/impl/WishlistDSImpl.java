package com.wishlist.gateway.database.impl;

import com.wishlist.core.entity.Wishlist;
import com.wishlist.gateway.database.WishlistDS;
import com.wishlist.gateway.database.model.WishlistDataModel;
import com.wishlist.gateway.database.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishlistDSImpl implements WishlistDS {

    private final WishlistRepository wishlistRepository;

    @Override
    public Optional<WishlistDataModel> findWishlistByIdAndClientid(String id, String clientId) {
        return wishlistRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public Optional<WishlistDataModel> findWishlistById(String id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public WishlistDataModel save(String idWishlist, Wishlist wishlist) {
        var wishlistDataModel = new WishlistDataModel(idWishlist, wishlist.getName(), wishlist.getClientId(), wishlist.getProductIds());
        return wishlistRepository.save(wishlistDataModel);
    }
}
