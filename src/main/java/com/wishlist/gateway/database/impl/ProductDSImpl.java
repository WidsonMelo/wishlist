package com.wishlist.gateway.database.impl;

import com.wishlist.gateway.database.ProductDS;
import com.wishlist.gateway.database.model.ProductDataModel;
import com.wishlist.gateway.database.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class ProductDSImpl implements ProductDS {

    private final ProductRepository productRepository;

    @Override
    public Optional<ProductDataModel> findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<ProductDataModel> findByIdIn(List<String> ids) {
        return StreamSupport.stream(productRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDataModel> findByNameLike(String name) {
        return productRepository.findByNameLike(name);
    }
}
