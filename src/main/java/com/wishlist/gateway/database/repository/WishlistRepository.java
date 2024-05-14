package com.wishlist.gateway.database.repository;


import com.wishlist.gateway.database.model.WishlistDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<WishlistDataModel, String> {

    Optional<WishlistDataModel> findByIdAndClientId(String id, String clientId);
    List<WishlistDataModel> findByNameLike(String name);
}
