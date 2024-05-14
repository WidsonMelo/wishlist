package com.wishlist.gateway.controller;

import com.wishlist.core.request.AddProductToWishlistRequest;
import com.wishlist.core.request.AddWishlistRequest;
import com.wishlist.core.response.*;
import com.wishlist.core.usecase.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Lista de Desejos")
@RequiredArgsConstructor
@RestController
public class WishlistController {

    private final AddProductToWishlistInput addProductToWishlistInput;
    private final AddWishlistInput addWishlistInput;
    private final FindWishlistByIdInput findWishlistByIdInput;
    private final FindProductFromWishlistInput findProductFromWishlistInput;
    private final CheckProductExistsMyWishlistInput checkProductExistsMyWishlistInput;
    private final RemoveProductFromWishlistInput removeProductFromWishlistInput;

    @Operation(summary = "Adicionar uma nova wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist adicionada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id do cliente ou nome da wishlist não forem informados.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se o cliente não existir.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @PostMapping("/wishlist")
    public AddWishlistResponse adicionarNovaWishlist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para nova wishlist.", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddWishlistRequest.class)))
            @RequestBody AddWishlistRequest request){
        return addWishlistInput.add(request.getName(), request.getClientId());
    }

    @Operation(summary = "Adicionar um produto na wishlist do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto adicionado com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddProductWishlistResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Produto ou wishlist não encontrados.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Wishlist cheia.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @PostMapping("/wishlist/{wishListId}/products")
    public AddProductWishlistResponse adicionarProdutoNaWishlist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para um produto a uma wishlist.", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddProductToWishlistRequest.class)))
            @RequestBody AddProductToWishlistRequest request, @PathVariable String wishListId){
        request.setId(wishListId);
        return addProductToWishlistInput.add(request);
    }

    @Operation(summary = "Consultar todos os produtos da wishlist do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FindProductsByWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id da wishlist não for informada.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se a wishlist não possui produtos.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/wishlist/{wishListId}/products")
    public FindProductsByWishlistResponse consultaProdutosPorWishlist(@Parameter(description = "Id da wishlist.") @PathVariable String wishListId) {
        return findProductFromWishlistInput.findProductsByWishlist(wishListId);
    }

    @Operation(summary = "Consultar se um determinado produto está na wishlist do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductExistsMyWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id do produto ou da wishlist não forem informados.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/wishlist/{wishlistId}/products/{productId}")
    public ProductExistsMyWishlistResponse verificaProdutosExisteNaWishlist(@Parameter(description = "Id do produto.") @PathVariable String productId,
                                                                            @Parameter(description = "Id da wishlist.") @PathVariable String wishlistId) {
        return checkProductExistsMyWishlistInput.checkProductExistsMyWishlist(wishlistId, productId);
    }

    @Operation(summary = "Busca uma wishlist pela Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FindWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se a id da wishlist não for informada.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se a wishlist não for encontrada.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/wishlist/{wishlistId}")
    public FindWishlistResponse consultaWishlistPorId(@Parameter(description = "Id da wishlist.") @PathVariable String wishlistId) {
        return findWishlistByIdInput.findById(wishlistId);
    }

    @Operation(summary = "Remover um produto da wishlist do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remoção do produto realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Se a id da wishlist ou do produto não forem informadas.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se o produto não for encontrado na wishlist, ou a wishlist não existe.",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @DeleteMapping("/wishlist/{wishListId}/products/{productId}")
    public void removerProdutoDaWishlist(@Parameter(description = "Id da wishlist.") @PathVariable String wishListId,
                                         @Parameter(description = "Id do produto.") @PathVariable String productId){
        removeProductFromWishlistInput.removeProductFromWishlistById(wishListId, productId);
    }
}
