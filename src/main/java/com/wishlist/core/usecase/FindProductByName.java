package com.wishlist.core.usecase;

import com.wishlist.core.response.FindProductResponseList;
import com.wishlist.core.usecase.interfaces.FindProductByNameInput;
import com.wishlist.exception.MissingDataException;
import com.wishlist.gateway.database.ProductDS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindProductByName implements FindProductByNameInput {

    private final ProductDS productDS;

    @Override
    public FindProductResponseList findByName(String name) {
        if (name == null)
            throw new MissingDataException("Nome não pode ser nulo.");

        var consultarProdutoResponseList = new FindProductResponseList();
        productDS.findByNameLike(name)
            .forEach(produto -> consultarProdutoResponseList.add(produto.getId(), produto.getName()));

        return consultarProdutoResponseList;
    }
}
