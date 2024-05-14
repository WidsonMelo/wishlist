package com.wishlist.gateway.database.repository;


import com.wishlist.gateway.database.model.ProductDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductDataModel, String> {
    List<ProductDataModel> findByNameLike(String name);
}
