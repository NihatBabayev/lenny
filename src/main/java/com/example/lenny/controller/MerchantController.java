package com.example.lenny.controller;

import com.example.lenny.dto.ProductRequest;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.entity.Product;
import com.example.lenny.security.JwtService;
import com.example.lenny.service.MerchantService;
import com.example.lenny.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/merchant")
public class MerchantController {
    private final ProductService productService;
    private final JwtService jwtService;

    @PostMapping("/product")
    public ResponseEntity<ResponseModel<String>> addProduct(@RequestBody ProductRequest product,
                                                            HttpServletRequest request) throws IOException {
        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
        return new ResponseEntity<>(productService.addProduct(product, username), HttpStatus.OK);
    }

//    @PostMapping("/product/photo")
//    public ResponseEntity<ResponseModel<String>> addProductPhoto(@RequestBody ProductPhotoDTO productPhotoDTO,
//                                                            HttpServletRequest request) throws IOException {
//        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
//
//        return new ResponseEntity<>(productService.addProductPhoto(username, productPhotoDTO), HttpStatus.OK);
//    }

//------------------------------------------------------------------------------------------
//    @PostMapping("/product")
//    public ResponseEntity<ResponseModel<String>> addProduct(@RequestBody Product product,
//                                                            HttpServletRequest request) throws IOException {
//        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
//        return new ResponseEntity<>(productService.addProduct(product, username), HttpStatus.OK);
//    }
//
//    @PostMapping("/product/photo")
//    public ResponseEntity<ResponseModel<String>> addProductPhoto(@RequestPart("photo") MultipartFile file,
//                                                                 @RequestParam("name") String productName,
//                                                                 HttpServletRequest request) throws IOException {
//        String username = jwtService.extractUsernameFromHeader(request.getHeader("Authorization"));
//
//        return new ResponseEntity<>(productService.addProductPhoto(username, productName, file), HttpStatus.OK);
//    }


}
