//package com.example.lenny.repository;
//
//import com.example.lenny.entity.Product;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ProductRepository extends MongoRepository<Product, String> {
//
//    //    @Query(value = "{}", fields = "{ '_id': 0, 'name': 1, 'rating': 1, 'soldNumber': 1 }")
//    //    List<ProductDTO> getPopularProducts(Sort sort);
//    List<Product> findAllBy(Sort sort);
//
//    Product findByNameAndMerchantEmail(String name, String merchantEmail);
//
//}
