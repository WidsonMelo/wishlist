package com.wishlist.gateway.database.repository;


import com.wishlist.gateway.database.model.ClientDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends MongoRepository<ClientDataModel, String> {

    List<ClientDataModel> findByNameLike(String name);
}
