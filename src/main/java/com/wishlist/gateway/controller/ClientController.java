package com.wishlist.gateway.controller;

import com.wishlist.core.response.FindClientResponseList;
import com.wishlist.core.response.DefaultResponse;
import com.wishlist.core.usecase.interfaces.FindClientByNameInput;
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

@Tag(name = "Cliente")
@RequiredArgsConstructor
@RestController
public class ClientController {

    private final FindClientByNameInput findClientByNameInput;

    @Operation(summary = "Busca um cliente que contém o nome informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FindClientResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome do cliente não for nulo.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/clients/{clientName}")
    public FindClientResponseList consultaClientePorNome(@Parameter(description = "Nome do cliente.") @PathVariable String clientName) {
        return findClientByNameInput.findByName(clientName);
    }

    @Operation(summary = "Consultar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FindClientResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome do cliente não for nulo.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/clients")
    public FindClientResponseList consultaClientes() {
        return findClientByNameInput.findAll();
    }
}
