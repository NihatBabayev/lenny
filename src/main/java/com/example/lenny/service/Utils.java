package com.example.lenny.service;

import com.example.lenny.dto.CommentDTO;
import com.example.lenny.dto.MerchantDTO;
import com.example.lenny.dto.ProductDTO;
import com.example.lenny.entity.Category;
import com.example.lenny.entity.Comment;
import com.example.lenny.entity.Photo;
import com.example.lenny.entity.Product;
import com.example.lenny.repository.CategoryRepository;
import com.example.lenny.repository.CommentRepository;
import com.example.lenny.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.lakeformation.paginators.SearchTablesByLFTagsIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Component
@RequiredArgsConstructor
public class Utils {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    public ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setRating(product.getRating());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setSoldNumber(product.getSoldNumber());
        dto.setPhoto(product.getPhoto());

        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setName(userRepository.findUserByMerchantId(product.getMerchant().getId()).getName());

        dto.setMerchant(merchantDTO);
        dto.setCategory(product.getCategory().getName());
        dto.setLocation(product.getLocation());
        Set<Photo> customPhotosSet = product.getPhotos();
        List<String> customNewPhotoList = new ArrayList<>();
        for (Photo photo:customPhotosSet) {
            customNewPhotoList.add(photo.getPhoto());
        }

//        List<String> photos = product.getPhotos().stream()
//                .map(Photo::getPhoto)
//                .collect(Collectors.toList());
        dto.setPhotos(customNewPhotoList);
        List<Comment> commentList = product.getComments().stream().collect(Collectors.toList());
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment: commentList) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setText(comment.getText());
            commentDTO.setAuthor(comment.getAuthor());
            commentDTO.setLikeNumber(comment.getLikeNumber());
            commentDTO.setLocalDateTime(comment.getLocalDateTime());
            commentDTO.setRating(comment.getRating());
            commentDTOList.add(commentDTO);
        }
        dto.setComments(commentDTOList);

        dto.setRatingFive(commentRepository.findNumberOfCommentsByRating(5, product.getId()));
        dto.setRatingFour(commentRepository.findNumberOfCommentsByRating(4, product.getId()));
        dto.setRatingThree(commentRepository.findNumberOfCommentsByRating(3, product.getId()));
        dto.setRatingTwo(commentRepository.findNumberOfCommentsByRating(2, product.getId()));
        dto.setRatingOne(commentRepository.findNumberOfCommentsByRating(1, product.getId()));
        dto.setTotalReviews(commentRepository.findTotalReviewsByProductId(product.getId()));

        return dto;
    }
}
