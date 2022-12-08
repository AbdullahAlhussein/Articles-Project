package com.example.demo.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ResponseEntity<?> createArticle(Article createArticle) {

        Map<String, String> returnCreateArticle = new HashMap<>();

        try{
        articleRepository.save(createArticle);
        returnCreateArticle.put("200", "Article created successfully");
        return ResponseEntity.ok().body(returnCreateArticle);

    } catch (Exception e)
        {
            returnCreateArticle.put("500","General Exception");
            return ResponseEntity.status(500).body(returnCreateArticle);
        }

    }

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public String getArticleImage(String id) {
        Long articleId = Long.parseLong(id);
        Article allInfoArticle = articleRepository.findById(articleId).orElse(null);
       return allInfoArticle.getImage();

    }

    public Article getArticle(String id) {
        Long articleId = Long.parseLong(id);
        return articleRepository.findById(articleId).orElse(null);
    }

    public void updateArticle(String id, Article data) {
        Long updatArticle = Long.parseLong(id);
        Article article = articleRepository.findById(updatArticle).orElse(null);

        if(article != null){
            article.setTitle(data.getTitle());
            article.setBody(data.getBody());
            article.setImage(data.getImage());
            article.setCreatedAt(data.getCreatedAt());
            articleRepository.save(article);
        }
    }

    public ResponseEntity<?>  deleteArticle(String id) {


        Map<String, String> returnCreateArticle = new HashMap<>();

        try{
            Long deleteId = Long.parseLong(id);
            articleRepository.deleteById(deleteId);

            returnCreateArticle.put("200", "Article delete successfully");
            return ResponseEntity.ok().body(returnCreateArticle);

        } catch (Exception e)
        {
            returnCreateArticle.put("500","General Exception");
            return ResponseEntity.status(500).body(returnCreateArticle);
        }
    }

    public List<Article> getArticleByName(String keyword) {

        return articleRepository.search(keyword);
    }

}
