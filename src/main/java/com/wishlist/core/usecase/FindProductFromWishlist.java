package com.wishlist.core.usecase;

import com.wishlist.core.response.FindProductsByWishlistResponse;
import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindProductFromWishlistInput;
import com.wishlist.core.usecase.interfaces.FindWishlistByIdInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.ProductDS;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class FindProductFromWishlist implements FindProductFromWishlistInput {

    private final ProductDS productDS;
    private final FindWishlistByIdInput consultarWishlistPorId;

    @Override
    public FindProductsByWishlistResponse findProductsByWishlist(String id) {
        if(StringUtils.isEmpty(id))
            throw new MissingDataException("É necessário informar o id da wishlist.");

        FindWishlistResponse wishlistResponse = consultarWishlistPorId.findById(id);
        if(CollectionUtils.isEmpty(wishlistResponse.getData().getProductIds()))
            throw new ResourceNotFoundException("A wishlist não possui produtos.");

        var response = new FindProductsByWishlistResponse();
        productDS.findByIdIn(wishlistResponse.getData().getProductIds())
                .forEach(produto -> response.add(produto.getId(), produto.getName()));
        return response;
    }
}
