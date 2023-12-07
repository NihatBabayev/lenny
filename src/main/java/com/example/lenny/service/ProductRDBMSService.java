package com.example.lenny.service;

import com.example.lenny.dto.ProductDTO;
import com.example.lenny.dto.ProductPhotoDTO;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.entity.ProductRDBMS;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductRDBMSService {
    ResponseModel<String> addProduct(ProductRDBMS product, String email);
    ResponseModel<List<ProductDTO>> getPopularProducts();
    ResponseModel<String> addProductPhoto(String username, ProductPhotoDTO productPhotoDTO) ;
}
