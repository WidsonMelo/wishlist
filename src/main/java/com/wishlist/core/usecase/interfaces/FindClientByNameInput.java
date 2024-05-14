package com.wishlist.core.usecase.interfaces;

import com.wishlist.core.response.FindClientResponseList;

public interface FindClientByNameInput {

    FindClientResponseList findByName(String name);
    FindClientResponseList findAll();
}
