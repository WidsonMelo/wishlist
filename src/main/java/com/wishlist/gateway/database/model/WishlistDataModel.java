package com.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("wishlist")
@Data
@AllArgsConstructor
public class WishlistDataModel {

    @Id
    private String id;
    private String name;
    private String clientId;
    private List<String> productIds;
}
