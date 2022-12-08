package com.example.demo.user;

import com.example.demo.article.Article;
import com.example.demo.reviews.Review;
import com.example.demo.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min = 2, message = "Name must be more than 2 characters!")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "mobile_number", unique = true)
    @NotBlank(message = "Mobile Number is required")
    @Size(min = 10, max = 20 ,message = "Mobile Number must be between 10 and 20 characters!")
    private String mobileNumber;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be more than 8 characters!")
    private String password;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Must add @ sign")
    private String email;


    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Article> article;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review> reviews;


}
