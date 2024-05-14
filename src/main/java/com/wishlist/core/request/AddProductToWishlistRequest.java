package com.wishlist.core.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class AddProductToWishlistRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String clientId;
    @NotBlank
    private String productId;

}
