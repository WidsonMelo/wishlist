package com.wishlist.core.usecase;

import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindWishlistByIdInput;
import com.wishlist.core.usecase.interfaces.RemoveProductFromWishlistInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RemoveProductFromWishlistById implements RemoveProductFromWishlistInput {

    private final WishlistDS wishlistDS;
    private final FindWishlistByIdInput findWishlistByIdInput;
    private final WishlistFactory wishlistFactory;

    @Override
    public void removeProductFromWishlistById(String wishlistId, String productId) {
        if(StringUtils.isEmpty(wishlistId))
            throw new MissingDataException("É necessário informar o id da wishlist.");
        if(StringUtils.isEmpty(productId))
            throw new MissingDataException("É necessário informar o id do produto.");

        FindWishlistResponse wishlistResponse = findWishlistByIdInput.findById(wishlistId);

        List<String> idProdutos = wishlistResponse.getData().getProductIds();
        if (!idProdutos.contains(productId))
            throw new ResourceNotFoundException("Produto não encontrado na wishlist.");

        idProdutos.remove(productId);

        var wishlist = wishlistFactory.create(wishlistResponse.getData().getName(), wishlistResponse.getData().getClientId(), idProdutos);
        wishlistDS.save(wishlistResponse.getData().getId(), wishlist);
    }
}
