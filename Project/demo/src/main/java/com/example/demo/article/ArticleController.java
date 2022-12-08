package com.example.demo.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/article")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<?> createArticle(@Valid @RequestBody Article article){
        return articleService.createArticle(article);
    }
    
    @GetMapping
    public List<Article> getAllArticle(){
        return articleService.getAllArticle();
    }

    @GetMapping("/{id}/image")
    public String getArticleImage(@PathVariable String id){
        return articleService.getArticleImage(id);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable String id){
        return articleService.getArticle(id);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable String id, @RequestBody Article article){
        articleService.updateArticle(id, article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteArticle(@PathVariable String id){
        return articleService.deleteArticle(id);
    }

    @GetMapping("/search/{keyword}")
    public List<Article> getAdsByName(@PathVariable String keyword){
        return articleService.getArticleByName(keyword);
    }
}
