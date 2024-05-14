package com.wishlist.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
@AllArgsConstructor
public class FindWishlistResponse extends DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String name;
        private String clientId;
        private List<String> productIds;
    }

    public FindWishlistResponse(String id, String name, String clientId, List<String> productIds) {
        data = new Data();
        data.id = id;
        data.name = name;
        data.clientId = clientId;
        data.productIds = productIds;
    }

}
