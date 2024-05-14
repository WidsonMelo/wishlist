package com.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

@Data
public class AddWishlistResponse extends com.wishlist.core.response.DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String name;
        private String clientId;
    }

    public AddWishlistResponse(String id, String name, String clientId) {
        data = new Data();
        data.id = id;
        data.name = name;
        data.clientId = clientId;
    }

}
