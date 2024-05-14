package com.wishlist.core.entity.factory.impl;

import com.wishlist.core.entity.Wishlist;
import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.entity.impl.WishlistImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WishlistFactoryImpl implements WishlistFactory {
    @Override
    public Wishlist create(String name, String clientId, List<String> productIds) {
        return new WishlistImpl(name, clientId, productIds);
    }
}
