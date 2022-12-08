package com.example.demo.reviews;

import com.example.demo.article.Article;
import com.example.demo.article.ArticleRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ArticleRepository articleRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public List<Review> getReviews(){return reviewRepository.findAll();}

    public Review getReviewId(String id){
        Long review_id = Long.parseLong(id);
        return reviewRepository.findById(review_id).orElse(null);
    }

    public Review saveReview(Review review, Long idUser, Long idArticle){

        User user = userRepository.findById(idUser).orElse(null);
        review.setUser(user);

        Article article =articleRepository.findById(idArticle).orElse(null);
        review.setArticle(article);

        return reviewRepository.save(review);
    }

}