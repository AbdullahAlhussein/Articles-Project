package com.example.demo.reviews;

import com.example.demo.article.Article;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    @Size(max = 100, message = "Note must be less than 100 characters !")
    private String comment;

    @Column(name="created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @JsonIgnoreProperties("reviews")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Article article;

}