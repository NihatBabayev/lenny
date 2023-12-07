package com.example.lenny.service.Impl;

import com.example.lenny.dto.ProductDTO;
import com.example.lenny.dto.ProductPhotoDTO;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.entity.Merchant;
import com.example.lenny.entity.ProductRDBMS;
import com.example.lenny.exception.PhotoDoesntExistException;
import com.example.lenny.exception.ProductIsNullException;
import com.example.lenny.repository.ProductRDBMSRepository;
import com.example.lenny.repository.UserRepository;
import com.example.lenny.service.ProductRDBMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRDBMSServiceImpl implements ProductRDBMSService {
    private final ProductRDBMSRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseModel<String> addProduct(ProductRDBMS product, String email) {
        if (product == null) {
            throw new ProductIsNullException();
        }
        Merchant merchant = userRepository.findByEmail(email).getMerchant();
        product.setMerchant(merchant);
        productRepository.save(product);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("product added successfully");
        return responseModel;
    }

    public ResponseModel<List<ProductDTO>> getPopularProducts() {
        List<ProductDTO> productDTOList = productRepository
                .findAll(Sort.by(Sort.Direction.DESC, "rating"))
                .stream()
                .map(ProductDTO::mapToDTO)
                .collect(Collectors.toList());

        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setData(productDTOList);
        responseModel.setMessage("Successfully retrieved products in descending order by rating");

        return responseModel;
    }
    @Override
    public ResponseModel<String> addProductPhoto(String username, ProductPhotoDTO productPhotoDTO) {
        if( productPhotoDTO.getProductName() == null){
            throw new ProductIsNullException();
        }
        if(productPhotoDTO.getPhoto() == null){
            throw new PhotoDoesntExistException();
        }
        ProductRDBMS product = productRepository.findByName(productPhotoDTO.getProductName());
        product.setPhoto(productPhotoDTO.getPhoto());
        productRepository.save(product);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("profile photo added successfully.");
        return responseModel;
    }
}
