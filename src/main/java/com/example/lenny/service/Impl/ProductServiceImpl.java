package com.example.lenny.service.Impl;

import com.example.lenny.dto.*;
import com.example.lenny.entity.*;
import com.example.lenny.exception.*;
import com.example.lenny.repository.*;
import com.example.lenny.service.ProductService;
import com.example.lenny.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;
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
    private final CustomerRepository customerRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistProductRepository wishlistProductRepository;

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
        if (productRequest.getPhotos() == null) {
            throw new PhotosDoesntExistException();
        }
        if (productRequest.getComments() == null || productRequest.getComments().isEmpty()) {
            throw new CommentsAreEmptyException();
        }
        product.setPhoto(productRequest.getPhoto());

        Merchant merchant = userRepository.findByEmail(email).getMerchant();
        product.setMerchant(merchant);

        Category category = categoryRepository.findByName(productRequest.getCategory());
        product.setCategory(category);

        productRepository.save(product);


        for (String photo : productRequest.getPhotos()
        ) {
            Product newProduct = productRepository.findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(product.getName(),
                    product.getCategory(),
                    product.getDescription(),
                    product.getLocation(),
                    product.getColor(),
                    product.getPrice(),
                    product.getSoldNumber());
            Photo newPhoto = new Photo();
            newPhoto.setPhoto(photo);
            newPhoto.setProduct(newProduct);
            photoRepository.save(newPhoto);
            newProduct.getPhotos().add(newPhoto);
            productRepository.save(newProduct);

        }

        for (Comment comment : productRequest.getComments()) {
            Product newProduct = productRepository.findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(product.getName(),
                    product.getCategory(),
                    product.getDescription(),
                    product.getLocation(),
                    product.getColor(),
                    product.getPrice(),
                    product.getSoldNumber());
            Comment newComment = new Comment();
            newComment.setProduct(newProduct);
            newComment.setRating(comment.getRating());
            newComment.setLikeNumber(comment.getLikeNumber());
            newComment.setText(comment.getText());
            newComment.setLocalDateTime(comment.getLocalDateTime());
            newComment.setAuthor(comment.getAuthor());
            commentRepository.save(newComment);
            newProduct.getComments().add(newComment);
            productRepository.save(newProduct);
        }

        Product newProduct = productRepository.findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getLocation(),
                product.getColor(),
                product.getPrice(),
                product.getSoldNumber());
        Integer ratingFive = commentRepository.findNumberOfCommentsByRating(5, newProduct.getId());
        Integer ratingFour = commentRepository.findNumberOfCommentsByRating(4, newProduct.getId());
        Integer ratingThree = commentRepository.findNumberOfCommentsByRating(3, newProduct.getId());
        Integer ratingTwo = commentRepository.findNumberOfCommentsByRating(2, newProduct.getId());
        Integer ratingOne = commentRepository.findNumberOfCommentsByRating(1, newProduct.getId());
        Integer totalReviews = commentRepository.findTotalReviewsByProductId(newProduct.getId());
        Integer productRating = (ratingFive * 5 + ratingFour * 4 + ratingThree * 3 + ratingTwo * 2 + ratingOne * 1) / totalReviews;
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
            filteredProducts = productRepository.findByFilterAndNameContainingIgnoreCase(location, category, start, end, searchKeyword.toLowerCase());
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

    @Override
    public ResponseModel<String> addProductToWishlistOfCustomer(ProductRequest productRequest, String username) {
        User user = userRepository.findByEmail(username);
        if (user.getCustomer() == null) {
            throw new UserIsNotCustomerException();
        }
        Customer customer = customerRepository.findByUser(user);
        if (customer.getWishlist() == null) {
            Wishlist wishlist = new Wishlist();
            wishlist.setCustomer(customer);
            wishlistRepository.save(wishlist);
        }
        Wishlist workingWishlist = wishlistRepository.findByCustomer(customer);
        Product product = productRepository.findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(productRequest.getName(),
                categoryRepository.findByName(productRequest.getCategory()),
                productRequest.getDescription(),
                productRequest.getLocation(),
                productRequest.getColor(),
                productRequest.getPrice(),
                productRequest.getSoldNumber());
        if (workingWishlist.getWishlistProducts().stream()
                .anyMatch(wishlistProduct -> wishlistProduct.getProductId().equals(product.getId()))) {
            throw new ProductAlreadyExistsInWishlistException();
        }
        WishlistProduct wishlistProduct = new WishlistProduct();
        wishlistProduct.setMarked(1);
        wishlistProduct.setWishlist(workingWishlist);
        wishlistProduct.setProductId(product.getId());
        wishlistProductRepository.save(wishlistProduct);

        workingWishlist.getWishlistProducts().add(wishlistProduct);
        wishlistRepository.save(workingWishlist);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Product added to cart successfully");
        return responseModel;
    }

    @Override
    public ResponseModel<List<ProductDTO>> getProductsInWishlistOfCustomer(String username) {
        User user = userRepository.findByEmail(username);
        if (user.getCustomer() == null) {
            throw new UserIsNotCustomerException();
        }
        Wishlist wishlist = wishlistRepository.findByCustomer(user.getCustomer());
        Set<WishlistProduct> productSet = wishlist.getWishlistProducts();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (WishlistProduct wishlistProduct : productSet) {
            Product product = productRepository.findById(wishlistProduct.getProductId()).get();
            ProductDTO productDTO = utils.mapToDTO(product);
            productDTO.setMarked(wishlistProduct.getMarked());
            productDTOs.add(productDTO);
        }
//        List<Product> productList = productSet.stream()
//                .map(WishlistProduct::getProductId)
//                .map(productId -> productRepository.findById(productId).get())
//                .collect(Collectors.toList());
//
//        List<ProductDTO> productDTOs = productList.stream()
//                .map(utils::mapToDTO)
//                .collect(Collectors.toList());

        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setMessage("Products In Shopping Cart");
        responseModel.setData(productDTOs);
        return responseModel;
    }


    @Override
    @Transactional
    public ResponseModel<List<ProductDTO>> deleteProductInWishlist(String username, String productName, String location) {
        User user = userRepository.findByEmail(username);
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserIsNotCustomerException();
        }
        Product product = productRepository.findByNameAndLocation(productName, location);
        Wishlist workingWishlist = wishlistRepository.findByCustomer(customer);
        Optional<WishlistProduct> matchingProduct = workingWishlist.getWishlistProducts().stream()
                .filter(wishlistProduct -> wishlistProduct.getProductId().equals(product.getId()))
                .findFirst();

        if (!matchingProduct.isPresent()) {
            throw new ProductDoesntExistInWishlistException();
        } else if (matchingProduct.isPresent()) {
            WishlistProduct wishlistProduct = matchingProduct.get();
            wishlistProductRepository.delete(wishlistProduct);
            workingWishlist.getWishlistProducts().remove(wishlistProduct);
            wishlistRepository.save(workingWishlist);
        }
        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setData(getProductsInWishlistOfCustomer(username).getData());
        responseModel.setMessage("Product deleted from shopping cart successfully");
        return responseModel;
    }

    @Override
    @Transactional
    public ResponseModel<List<ProductDTO>> undoProductInWishlist(String username, String productName, String location) throws InterruptedException {
        User user = userRepository.findByEmail(username);
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserIsNotCustomerException();
        }
        Product product = productRepository.findByNameAndLocation(productName, location);
        Wishlist workingWishlist = wishlistRepository.findByCustomer(customer);
        Optional<WishlistProduct> matchingProduct = workingWishlist.getWishlistProducts().stream()
                .filter(wishlistProduct -> wishlistProduct.getProductId().equals(product.getId()))
                .findFirst();

        if (!matchingProduct.isPresent()) {
            throw new ProductDoesntExistInWishlistException();
        } else if (matchingProduct.isPresent()) {
            WishlistProduct wishlistProduct = matchingProduct.get();
            if (wishlistProduct.getMarked() == 0) {
                throw new ProductAlreadyUnmarkedException();
            }
            wishlistProductRepository.setMarkedField(wishlistProduct.getId(), 0);
            wishlistRepository.save(workingWishlist);
        }
        workingWishlist = wishlistRepository.findByCustomer(customer);

        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setMessage("Product unmarked");
        responseModel.setData(getProductsInWishlistOfCustomer(username).getData());
        return responseModel;
    }

    @Override
    @Transactional
    public ResponseModel<List<ProductDTO>> markProductInWishlist(String username, String productName, String location) {
        User user = userRepository.findByEmail(username);
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserIsNotCustomerException();
        }
        Product product = productRepository.findByNameAndLocation(productName, location);
        Wishlist workingWishlist = wishlistRepository.findByCustomer(customer);
        Optional<WishlistProduct> matchingProduct = workingWishlist.getWishlistProducts().stream()
                .filter(wishlistProduct -> wishlistProduct.getProductId().equals(product.getId()))
                .findFirst();

        if (!matchingProduct.isPresent()) {
            throw new ProductDoesntExistInWishlistException();
        } else if (matchingProduct.isPresent()) {
            WishlistProduct wishlistProduct = matchingProduct.get();
            if (wishlistProduct.getMarked() == 1) {
                throw new ProductAlreadyMarkedException();
            }
            wishlistProductRepository.setMarkedField(wishlistProduct.getId(), 1);
            wishlistRepository.save(workingWishlist);
        }
        ResponseModel<List<ProductDTO>> responseModel = new ResponseModel<>();
        responseModel.setMessage("Product unmarked");
        responseModel.setData(getProductsInWishlistOfCustomer(username).getData());
        return responseModel;
    }

    @Override
    @Transactional
    public ResponseModel<CheckoutDTO> checkoutWishlistOfCustomer(String username) {
        User user = userRepository.findByEmail(username);
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserIsNotCustomerException();
        }
        List<CheckoutProductDTO> checkoutProductDTOList = new ArrayList<>();
        for (WishlistProduct wishlistProduct: wishlistRepository.findByCustomer(customer).getWishlistProducts()) {
            CheckoutProductDTO checkoutProductDTO = new CheckoutProductDTO();
            checkoutProductDTO.setProductName(productRepository.findById(wishlistProduct.getProductId()).get().getName());
            checkoutProductDTO.setPrice(productRepository.findById(wishlistProduct.getProductId()).get().getPrice());
            checkoutProductDTOList.add(checkoutProductDTO);
            wishlistProductRepository.deleteById(wishlistProduct.getId());
        }
        double totalPrice = 0.0;
        int inc = 0;
        for (CheckoutProductDTO checkoutProductDTO:checkoutProductDTOList) {
            inc++;
            totalPrice+= checkoutProductDTO.getPrice();
        }
        double shippingDiscount = totalPrice/10;
        double taxAndFee = totalPrice/40;
        double finalPrice = totalPrice - shippingDiscount + taxAndFee;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        totalPrice = Double.parseDouble(decimalFormat.format(totalPrice));
        shippingDiscount = Double.parseDouble(decimalFormat.format(shippingDiscount));
        taxAndFee = Double.parseDouble(decimalFormat.format(taxAndFee));
        finalPrice = Double.parseDouble(decimalFormat.format(finalPrice));

        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setProducts(checkoutProductDTOList);
        checkoutDTO.setTotalPrice(totalPrice);
        checkoutDTO.setShippingDiscount(shippingDiscount);
        checkoutDTO.setTaxAndFee(taxAndFee);
        checkoutDTO.setFinalPrice(finalPrice);
        wishlistRepository.deleteByCustomer_Id(customer.getId());

        ResponseModel<CheckoutDTO> responseModel = new ResponseModel<>();
        responseModel.setData(checkoutDTO);
        responseModel.setMessage("Success");
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