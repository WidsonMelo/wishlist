package com.wishlist.core.usecase;

import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.request.AddProductToWishlistRequest;
import com.wishlist.core.response.AddProductWishlistResponse;
import com.wishlist.core.usecase.interfaces.AddProductToWishlistInput;
import com.wishlist.core.usecase.interfaces.CheckProductExistsInput;
import com.wishlist.exception.InvalidOperationException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import com.wishlist.gateway.database.model.WishlistDataModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddProductToWishlist implements AddProductToWishlistInput {

    private final WishlistDS wishlistDS;
    private final CheckProductExistsInput consultarProduto;
    private final WishlistFactory wishlistFactory;

    @Override
    public AddProductWishlistResponse add(AddProductToWishlistRequest wishlistRequest) {
        Optional<WishlistDataModel> optionalWishlistDataModel = wishlistDS.findWishlistByIdAndClientid(
                wishlistRequest.getId(), wishlistRequest.getClientId());
        if (optionalWishlistDataModel.isEmpty())
            throw new ResourceNotFoundException("Wishlist não existe.");
        var wishlistDataModel = optionalWishlistDataModel.get();

        if(!consultarProduto.checkProductExistsById(wishlistRequest.getProductId()))
            throw new ResourceNotFoundException("Produto não existe.");

        var wishlist = wishlistFactory.create(wishlistDataModel.getName(), wishlistDataModel.getClientId(), wishlistDataModel.getProductIds());

        if(wishlist.isFullWishlist())
            throw new InvalidOperationException("Wishlist já está cheia.");

        wishlist.getProductIds().add(wishlistRequest.getProductId());

        wishlistDS.save(wishlistDataModel.getId(), wishlist);
        return new AddProductWishlistResponse(wishlistDataModel.getId(),
                wishlist.getClientId(), wishlist.getProductIds());
    }
}
