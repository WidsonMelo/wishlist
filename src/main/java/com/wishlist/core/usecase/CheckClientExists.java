package com.wishlist.core.usecase;

import com.wishlist.core.usecase.interfaces.CheckClientExistsInput;
import com.wishlist.gateway.database.ClientDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckClientExists implements CheckClientExistsInput {

    private final ClientDS clientDS;

    @Override
    public boolean checkClientExistsById(String id) {
        if (id == null)
            return false;
        return clientDS.findById(id).isPresent();
    }
}
