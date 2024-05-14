package com.wishlist.gateway.database;

import com.wishlist.gateway.database.model.ProductDataModel;

import java.util.List;
import java.util.Optional;

public interface ProductDS {
    Optional<ProductDataModel> findById(String productId);
    List<ProductDataModel> findByIdIn(List<String> ids);
    List<ProductDataModel> findByNameLike(String name);
}
