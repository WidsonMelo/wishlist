package com.wishlist.core.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AddWishlistRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String clientId;

}
