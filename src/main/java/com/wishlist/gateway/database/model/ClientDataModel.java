package com.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("client")
@Data
@AllArgsConstructor
public class ClientDataModel {

    @Id
    private String id;
    private String name;
}
