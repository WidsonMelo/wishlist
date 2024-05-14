package com.wishlist.core.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductExistsMyWishlistResponse extends DefaultResponse {

    private boolean data = false;

    public ProductExistsMyWishlistResponse(boolean data){
        this.data = data;
    }
}
