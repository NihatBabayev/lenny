package com.example.lenny.service;

import com.example.lenny.dto.*;
import com.example.lenny.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ResponseModel<String> addProduct(ProductRequest productRequest, String email);
    ResponseModel<List<ProductDTO>> getPopularProducts();

    ResponseModel<List<ProductDTO>> getProductByFilter(String location, String category, String priceStart, String priceEnd, String searchKeyword);

    ResponseModel<String> addProductToWishlistOfCustomer(ProductRequest productRequest, String username);

    ResponseModel<List<ProductDTO>> getProductsInWishlistOfCustomer(String username);

    ResponseModel<List<ProductDTO>> deleteProductInWishlist(String username, String productName, String location);

    ResponseModel<List<ProductDTO>> undoProductInWishlist(String username, String productName, String location);

    ResponseModel<List<ProductDTO>> markProductInWishlist(String username, String productName, String location);

    ResponseModel<CheckoutDTO> checkoutWishlistOfCustomer(String username);

//    ResponseModel<String> addProductPhoto(String username, ProductPhotoDTO productPhotoDTO) ;

//    ResponseModel<String> getPhotoOfProduct(PhotoRequestDTO photoRequestDTO);

}
