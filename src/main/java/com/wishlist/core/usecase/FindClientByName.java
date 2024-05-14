package com.wishlist.core.usecase;

import com.wishlist.core.usecase.interfaces.FindClientByNameInput;
import com.wishlist.core.response.FindClientResponseList;
import com.wishlist.exception.MissingDataException;
import com.wishlist.gateway.database.ClientDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindClientByName implements FindClientByNameInput {

    private final ClientDS clientDS;

    @Override
    public FindClientResponseList findByName(String name) {
        if (name == null)
            throw new MissingDataException("Nome não pode ser nulo.");

        var consultarClienteResponseList = new FindClientResponseList();
        clientDS.findByNameLike(name).stream()
                .forEach(cliente -> consultarClienteResponseList.add(cliente.getId(), cliente.getName()));

        return consultarClienteResponseList;
    }

    @Override
    public FindClientResponseList findAll() {
        var consultarClienteResponseList = new FindClientResponseList();

        clientDS.findAll()
                .forEach(cliente -> consultarClienteResponseList.add(cliente.getId(), cliente.getName()));
        return consultarClienteResponseList;
    }
}
