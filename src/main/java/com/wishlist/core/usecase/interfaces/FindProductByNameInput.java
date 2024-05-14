package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.FindProductResponseList;

public interface FindProductByNameInput {
    FindProductResponseList findByName(String name);
}
