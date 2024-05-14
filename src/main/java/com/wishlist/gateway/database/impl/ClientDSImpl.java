package com.wishlist.gateway.database.impl;

import com.wishlist.gateway.database.ClientDS;
import com.wishlist.gateway.database.model.ClientDataModel;
import com.wishlist.gateway.database.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientDSImpl implements ClientDS {

    private final ClientRepository clientRepository;

    @Override
    public Optional<ClientDataModel> findById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<ClientDataModel> findByNameLike(String nome) {
        return clientRepository.findByNameLike(nome);
    }

    @Override
    public List<ClientDataModel> findAll() {
        return clientRepository.findAll();
    }
}
