package com.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class AddProductWishlistResponse extends DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String clientId;
        private List<String> productIds;
    }

    public AddProductWishlistResponse(String id, String clientId, List<String> productId) {
        data = new AddProductWishlistResponse.Data();
        data.id = id;
        data.clientId = clientId;
        data.productIds = productId;
    }
}
