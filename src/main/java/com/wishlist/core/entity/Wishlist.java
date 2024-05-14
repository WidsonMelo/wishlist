package com.wishlist.core.entity;

import java.util.List;

public interface Wishlist {

    String getName();

    String getClientId();

    List<String> getProductIds();

    boolean isFullWishlist();
}
