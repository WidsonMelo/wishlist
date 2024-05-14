package com.wishlist.core.usecase;

import com.wishlist.core.response.FindWishlistResponse;
import com.wishlist.core.usecase.interfaces.FindWishlistByIdInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import com.wishlist.gateway.database.model.WishlistDataModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FindWishlistById implements FindWishlistByIdInput {

    private final WishlistDS wishlistDS;

    @Override
    public FindWishlistResponse findById(String id) {
        if(StringUtils.isEmpty(id))
            throw new MissingDataException("Id deve ser preenchida.");
        Optional<WishlistDataModel> wishlistDataModelOptional = wishlistDS.findWishlistById(id);
        if(wishlistDataModelOptional.isEmpty())
            throw new ResourceNotFoundException("Wishlist não encontrada.");
        var dataModel = wishlistDataModelOptional.get();
        return new FindWishlistResponse(dataModel.getId(), dataModel.getName(), dataModel.getClientId(), dataModel.getProductIds());
    }
}
