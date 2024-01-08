package com.example.lenny.controller;

import com.example.lenny.dto.PhotoRequestDTO;
import com.example.lenny.dto.ProductDTO;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

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
                                                                               @RequestParam(value = "search", required = false) String searchKeyword) {
        return new ResponseEntity<>(productService.getProductByFilter(location, category, price_start, price_end, searchKeyword), HttpStatus.OK);
    }
}
