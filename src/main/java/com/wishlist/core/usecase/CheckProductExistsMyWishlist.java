package com.wishlist.core.usecase;

import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.response.ProductExistsMyWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindWishlistByIdInput;
import com.wishlist.core.usecase.interfaces.CheckProductExistsMyWishlistInput;
import com.wishlist.exception.MissingDataException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class CheckProductExistsMyWishlist implements CheckProductExistsMyWishlistInput {

    private final FindWishlistByIdInput consultarWishlistPorId;

    @Override
    public ProductExistsMyWishlistResponse checkProductExistsMyWishlist(String wishlistId, String productId) {
        if(StringUtils.isEmpty(wishlistId))
            throw new MissingDataException("É necessário informar o id da wishlist.");
        if(StringUtils.isEmpty(productId))
            throw new MissingDataException("É necessário informar o id do produto.");

        FindWishlistResponse wishlistResponse = consultarWishlistPorId.findById(wishlistId);
        if(CollectionUtils.isEmpty(wishlistResponse.getData().getProductIds()))
            return new ProductExistsMyWishlistResponse(false);

        return new ProductExistsMyWishlistResponse(wishlistResponse.getData().getProductIds()
                .stream()
                .anyMatch(id -> id.equals(productId)));
    }
}
