package com.example.lenny.controller;

import com.example.lenny.dto.ProductDTO;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.service.Impl.ProductRDBMSServiceImpl;
import com.example.lenny.service.ProductRDBMSService;
//import com.example.lenny.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
//    private final ProductService productService;
    private final ProductRDBMSServiceImpl productService;
    @GetMapping("/popular")
    public ResponseEntity<ResponseModel<List<ProductDTO>>> getPopularProducts(){
        return new ResponseEntity<>(productService.getPopularProducts(), HttpStatus.OK);
    }
}
