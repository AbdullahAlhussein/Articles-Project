package com.example.demo.article;

import com.example.demo.category.Category;
import com.example.demo.reviews.Review;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@Data
public class Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Size(max = 100, message = "Note must be less than 256 characters !")
    @NotBlank(message = "title is required")
    private String title;

    @Column(name = "body")
    @Size(max = 500, message = "Note must be less than 256 characters !")
    @NotBlank(message = "body is required")
    private String body;

    @Column(name = "image")
    @Size(max = 500, message = "Note must be less than 256 characters !")
    private String image;

    @Column(name="created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("article")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private User user;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("article")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Category category;

    @JsonIgnoreProperties("article")
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Review> reviews;

}
