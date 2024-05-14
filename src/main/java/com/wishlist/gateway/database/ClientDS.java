package com.wishlist.gateway.database;

import com.wishlist.gateway.database.model.ClientDataModel;

import java.util.List;
import java.util.Optional;

public interface ClientDS {
    Optional<ClientDataModel> findById(String id);
    List<ClientDataModel> findByNameLike(String nome);
    List<ClientDataModel> findAll();
}
