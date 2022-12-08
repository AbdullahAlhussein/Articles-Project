package com.example.demo.category;

import com.example.demo.article.Article;
import com.example.demo.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "categoryName")
    @NotBlank(message = "Category Name is required")
    private String categoryName;

    @JsonIgnoreProperties("category")
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Article> article;


}
