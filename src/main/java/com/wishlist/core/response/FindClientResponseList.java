package com.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class FindClientResponseList extends DefaultResponse {

    private List<Data> data = new ArrayList<>();

    @Getter
    public class Data {
        private String id;
        private String name;
    }

    public FindClientResponseList(String id, String name){
        this.add(id, name);
    }
    public FindClientResponseList(){}

    public void add(String id, String nome){
        var response = new Data();
        response.id = id;
        response.name = nome;
        this.data.add(response);
    }
}
