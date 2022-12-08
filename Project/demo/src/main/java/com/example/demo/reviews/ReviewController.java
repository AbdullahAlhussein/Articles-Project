package com.example.demo.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="comment")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews(){return reviewService.getReviews();}



    @PostMapping
    public Review addOne(@RequestBody Form form){
        return reviewService.saveReview(form.getReview(),form.getIdUser(),form.getIdArticle());
    }


}
class Form {

    private Review review;
    private Long idUser;
    private Long idArticle;


    public Review getReview() {
        return review;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdArticle() {
        return idArticle;
    }
}