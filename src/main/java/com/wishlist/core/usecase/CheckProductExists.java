package com.wishlist.core.usecase;

import com.wishlist.core.usecase.interfaces.CheckProductExistsInput;
import com.wishlist.gateway.database.ProductDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckProductExists implements CheckProductExistsInput {

    private final ProductDS productDS;

    @Override
    public boolean checkProductExistsById(String id) {
        if (id == null)
            return false;
        return productDS.findById(id).isPresent();
    }
}
