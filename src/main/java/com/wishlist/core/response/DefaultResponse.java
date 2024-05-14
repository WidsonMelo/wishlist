package com.wishlist.core.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {
    protected String status = "SUCESSO";
    protected List<String> messages = new ArrayList<>();

    public void setMessagem(String message){
        messages = Arrays.asList(message);
    }

    public void add(String message) {
        messages.add(message);
    }

    public DefaultResponse(String status, String message){
        this.status = status;
        setMessagem(message);
    }
}
