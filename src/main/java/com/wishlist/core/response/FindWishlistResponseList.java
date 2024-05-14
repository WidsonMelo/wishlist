package com.wishlist.core.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FindWishlistResponseList extends DefaultResponse {

    private List<Data> data = new ArrayList<>();

    @Getter
    public class Data {
        private String id;
        private String name;
        private String clientId;
        private List<String> productIds;
    }

    public FindWishlistResponseList(String id, String name, String clientId, List<String> productIds){
        this.add(id, name, clientId, productIds);
    }

    public void add(String id, String nome,String idCliente, List<String> idProdutos){
        var dados = new Data();
        dados.id = id;
        dados.name = nome;
        dados.clientId = idCliente;
        dados.productIds = idProdutos;
        this.data.add(dados);
    }
}
