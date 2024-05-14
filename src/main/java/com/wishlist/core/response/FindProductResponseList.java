package com.wishlist.core.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FindProductResponseList extends DefaultResponse {

    private List<Data> data = new ArrayList<>();

    @Getter
    public class Data {
        private String id;
        private String name;
    }

    public FindProductResponseList(String id, String name){
        this.add(id, name);
    }

    public void add(String id, String nome){
        var response = new Data();
        response.id = id;
        response.name = nome;
        this.data.add(response);
    }
}
