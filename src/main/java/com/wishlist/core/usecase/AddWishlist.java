package com.wishlist.core.usecase;

import com.wishlist.core.entity.factory.WishlistFactory;
import com.wishlist.core.response.AddWishlistResponse;
import com.wishlist.core.usecase.interfaces.AddWishlistInput;
import com.wishlist.core.usecase.interfaces.CheckClientExistsInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.gateway.database.WishlistDS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddWishlist implements AddWishlistInput {

    private final WishlistDS wishlistDS;
    private final CheckClientExistsInput consultarCliente;
    private final WishlistFactory wishlistFactory;

    @Override
    public AddWishlistResponse add(String name, String clientId) {

        if (name == null || name.isBlank())
            throw new MissingDataException("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
        if (clientId == null || clientId.isBlank())
            throw new MissingDataException("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
        if(!consultarCliente.checkClientExistsById(clientId))
            throw new ResourceNotFoundException("Cliente não encontrado.");

        var wishlist = wishlistFactory.create(name, clientId, List.of());
        var wishlistDataModel = wishlistDS.save(null, wishlist);

        return new AddWishlistResponse(wishlistDataModel.getId(),
                wishlistDataModel.getName(), wishlistDataModel.getClientId());
    }
}
