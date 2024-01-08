package com.example.lenny.service.Impl;

import com.example.lenny.dto.*;
import com.example.lenny.entity.*;
import com.example.lenny.exception.PhotoDoesntExistException;
import com.example.lenny.exception.ProductIsNullException;
import com.example.lenny.repository.*;
import com.example.lenny.service.ProductService;
import com.example.lenny.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Utils utils;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseModel<String> addProduct(ProductRequest productRequest, String email) {
        if (productRequest == null) {
            throw new ProductIsNullException();
        }
        Product product = new Product();
        if (productRequest.getName() == null) {
            throw new ProductIsNullException();
        }
        product.setName(productRequest.getName());
        product.setColor(productRequest.getColor());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setSoldNumber(productRequest.getSoldNumber());
        product.setLocation(productRequest.getLocation());
        if (productRequest.getPhoto() == null) {
            throw new PhotoDoesntExistException();
        }
        product.setPhoto(productRequest.getPhoto());

        Merchant merchant = userRepository.findByEmail(email).getMerchant();
        product.setMerchant(merchant);

        Category category = categoryRepository.findByName(productRequest.getCategory());
        product.setCategory(category);

        productRepository.save(product);

        Product newProduct = productRepository.findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getLocation(),
                product.getColor(),
                product.getPrice(),
                product.getSoldNumber());

        for (String photo : productRequest.getPhotos()
        ) {
            Photo newPhoto = new Photo();
            newPhoto.setPhoto(photo);
            newPhoto.setProduct(newProduct);
            photoRepository.save(newPhoto);
        }

        for (Comment comment : productRequest.getComments()
        ) {
            comment.setProduct(newProduct);
            commentRepository.save(comment);
        }

        Integer ratingFive = commentRepository.findNumberOfCommentsByRating(5.0, newProduct.getId());
        Integer ratingFour = commentRepository.findNumberOfCommentsByRating(4.0, newProduct.getId());
        Integer ratingThree = commentRepository.findNumberOfCommentsByRating(3.0, newProduct.getId());
        Integer ratingTwo = commentRepository.findNumberOfCommentsByRating(2.0, newProduct.getId());
        Integer ratingOne = commentRepository.findNumberOfCommentsByRating(1.0, newProduct.getId());
        Double totalReviews = commentRepository.findTotalReviewsByProductId(newProduct.getId()).doubleValue();
        Double productRating = (ratingFive * 5 + ratingFour * 4 + ratingThree * 3 + ratingTwo * 2 + ratingOne * 1) / totalReviews ;
        newProduct.setRating(productRating);
        productRepository.save(newProduct);

        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("product added successfully");
        return responseModel;
    }

    public ResponseModel<List<ProductDTO>> getPopularProducts() {
        List<ProductDTO> productDTOList = productRepository
                .findAll(Sort.by(Sort.Direction.DESC, "rating"))
                .stream()
                .map(utils::mapToDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setData(productDTOList);
        responseModel.setMessage("Successfully retrieved products in descending order by rating");
        return responseModel;
    }

    @Override
    public ResponseModel<List<ProductDTO>> getProductByFilter(String location, String category, String priceStart, String priceEnd, String searchKeyword) {
        Double start = (priceStart != null && !priceStart.isEmpty()) ? Double.parseDouble(priceStart) : null;
        Double end = (priceEnd != null && !priceEnd.isEmpty()) ? Double.parseDouble(priceEnd) : null;

        List<Product> filteredProducts;

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // If a search keyword is provided, perform the search in addition to other filters
            filteredProducts = productRepository.findByFilterAndNameContainingIgnoreCase(location, category, start, end, searchKeyword);
        } else {
            // If no search keyword, use only the specified filters
            filteredProducts = productRepository.findByFilter(location, category, start, end);
        }

        // Map the filtered products to DTOs using the Utils class
        List<ProductDTO> productDTOs = filteredProducts.stream()
                .map(utils::mapToDTO)
                .collect(Collectors.toList());

        // Create and return the response model
        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setMessage("Filtered result");
        responseModel.setData(productDTOs);
        return responseModel;
    }


//    @Override
//    public ResponseModel<String> addProductPhoto(String username, ProductPhotoDTO productPhotoDTO) {
//        if( productPhotoDTO.getProductName() == null){
//            throw new ProductIsNullException();
//        }
//        if(productPhotoDTO.getPhoto() == null){
//            throw new PhotoDoesntExistException();
//        }
//        Product product = productRepository.findByName(productPhotoDTO.getProductName());
//        product.setPhoto(productPhotoDTO.getPhoto());
//        productRepository.save(product);
//        ResponseModel<String> responseModel = new ResponseModel<>();
//        responseModel.setMessage("profile photo added successfully.");
//        return responseModel;
//    }

//    @Override
//    public ResponseModel<String> getPhotoOfProduct(PhotoRequestDTO photoRequestDTO) {
//        ResponseModel<String> responseModel = new ResponseModel<>();
//        responseModel.setData(productRepository.findByName(photoRequestDTO.getProductName()).getPhoto());
//        responseModel.setMessage("Successfully retrieved");
//        return responseModel;
//    }
}