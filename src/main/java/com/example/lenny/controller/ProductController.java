package com.example.lenny.controller;

import com.example.lenny.dto.*;
import com.example.lenny.security.JwtService;
import com.example.lenny.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;
    private final JwtService jwtService;

    @GetMapping("/popular")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> getPopularProducts() {
        return new ResponseEntity<>(productService.getPopularProducts(), HttpStatus.OK);
    }

    //    @GetMapping("/photo")
//    public ResponseEntity<ResponseModel<String>> getPhoto(@RequestBody PhotoRequestDTO photoRequestDTO){
//        return new ResponseEntity<>(productService.getPhotoOfProduct(photoRequestDTO), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<ResponseModel<List<ProductDTO>>> getProductsByFilter(@RequestParam(value = "location", required = false) String location,
                                                                               @RequestParam(value = "category", required = false) String category,
                                                                               @RequestParam(value = "price_start", required = false) String price_start,
                                                                               @RequestParam(value = "price_end", required = false) String price_end,
                                                                               @RequestParam(value = "search_query", required = false) String searchKeyword) {
        return new ResponseEntity<>(productService.getProductByFilter(location, category, price_start, price_end, searchKeyword), HttpStatus.OK);
    }
    @PostMapping("/cart")
    public ResponseEntity<ResponseModel<String>> addProductToWishlistOfCustomer(@RequestBody ProductRequest productRequest,
                                                                                HttpServletRequest request){

        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.addProductToWishlistOfCustomer(productRequest, username), HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> getProductsInWishlistOfCustomer(HttpServletRequest request){
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.getProductsInWishlistOfCustomer(username), HttpStatus.OK);
    }
    @PostMapping("/cart/delete")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> deleteProductInWishlist(@RequestParam("product_name") String productName,
                                                                       @RequestParam("product_location") String location,
                                                                       HttpServletRequest request){
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.deleteProductInWishlist(username, productName, location), HttpStatus.OK);
    }
    @PostMapping("/cart/undo")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> undoProductInWishlist(@RequestParam("product_name") String productName,
                                                                                 @RequestParam("product_location") String location,
                                                                                 HttpServletRequest request){
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.undoProductInWishlist(username, productName, location), HttpStatus.OK);
    }
    @PostMapping("/cart/mark")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> markProductInWishlist(@RequestParam("product_name") String productName,
                                                                                 @RequestParam("product_location") String location,
                                                                                 HttpServletRequest request){
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.markProductInWishlist(username, productName, location), HttpStatus.OK);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<ResponseModel<CheckoutDTO>> checkoutProductsInWishlist(HttpServletRequest request){
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.checkoutWishlistOfCustomer(username), HttpStatus.OK);

    }
}
