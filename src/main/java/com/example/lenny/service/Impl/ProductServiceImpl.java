//package com.example.lenny.service.Impl;
//
//import com.example.lenny.dto.MerchantDTO;
//import com.example.lenny.dto.ProductDTO;
//import com.example.lenny.dto.ResponseModel;
//import com.example.lenny.entity.Merchant;
//import com.example.lenny.entity.Product;
//import com.example.lenny.entity.User;
//import com.example.lenny.exception.ProductIsNullException;
//import com.example.lenny.repository.ProductRepository;
//import com.example.lenny.repository.UserRepository;
//import com.example.lenny.service.ProductService;
//import com.example.lenny.service.S3Service;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ProductServiceImpl implements ProductService {
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
//    private final S3Service s3Service;
//
//    @Override
//    public List<ProductDTO> getPopularProducts() {
////        return productRepository.getPopularProducts(Sort.by(Sort.Order.desc("rating")));
//        List<Product> productList = productRepository.findAllBy(Sort.by(Sort.Order.desc("rating")));
//        List<ProductDTO> productDTOList = new ArrayList<>();
//        for (Product product:
//             productList) {
//            ProductDTO productDTO =  new ProductDTO();
//            productDTO.setName(product.getName());
//            productDTO.setRating(product.getRating());
//            productDTO.setSoldNumber(product.getSoldNumber());
//            productDTOList.add(productDTO);
//        }
//        return productDTOList;
//    }
//
//    @Override
//    public ResponseModel<String> addProduct(Product product, String email) throws IOException {
//        if(product == null){
//            throw new ProductIsNullException();
//        }
//        MerchantDTO merchant = new MerchantDTO();
//        merchant.setEmail(email);
//        merchant.setName(userRepository.findByEmail(email).getName());
//        product.setMerchant(merchant);
//        productRepository.save(product);
//        ResponseModel<String> responseModel = new ResponseModel<>();
//        responseModel.setMessage("product added successfully");
//        return responseModel;
//    }
//
//    @Override
//    public ResponseModel<String> addProductPhoto(String username, String productName, MultipartFile file) throws IOException {
//        Product product = productRepository.findByNameAndMerchantEmail(productName, username);
//        if(product == null){
//            throw new ProductIsNullException();
//        }
//        String photoName = "products/" + product.getCategory() +"/"+ UUID.randomUUID() + file.getOriginalFilename();
//        product.setPhotoName(photoName);
//        productRepository.save(product);
//        s3Service.uploadPhoto(photoName, file);
//        ResponseModel<String> responseModel = new ResponseModel<>();
//        responseModel.setMessage("product photo added successfully");
//        return responseModel;
//
//    }
//
//}
