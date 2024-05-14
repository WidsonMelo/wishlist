package com.wishlist.gateway.controller;

import com.wishlist.core.response.FindProductResponseList;
import com.wishlist.core.response.DefaultResponse;
import com.wishlist.core.usecase.interfaces.FindProductByNameInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Produto")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final FindProductByNameInput findProductByNameInput;

    @Operation(summary = "Busca um produto que contém o nome informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FindProductResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome do produto for nulo.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/products/{productName}")
    public FindProductResponseList consultaProdutoPorNome(@Parameter(description = "Nome do produto.") @PathVariable String nome) {
        return findProductByNameInput.findByName(nome);
    }
}
