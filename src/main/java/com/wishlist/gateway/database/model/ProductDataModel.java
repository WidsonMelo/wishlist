package com.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@Data
@AllArgsConstructor
public class ProductDataModel {

    @Id
    private String id;
    private String name;
}
