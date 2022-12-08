package com.example.demo.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<?> createCategory(Category createCategory) {

        Map<String, String> returnCreateArticle = new HashMap<>();

        try{
            categoryRepository.save(createCategory);
            returnCreateArticle.put("200", "Category created successfully");
            return ResponseEntity.ok().body(returnCreateArticle);

        } catch (Exception e)
        {
            returnCreateArticle.put("500","General Exception");
            return ResponseEntity.status(500).body(returnCreateArticle);
        }
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategory(String id) {
        Long categoryId =Long.parseLong(id);
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public List<Category> getCategoryByName(String name) {

        return categoryRepository.findByCategoryName(name);
    }

    public void updateCategory(String id, Category data) {
        Long updateCategory = Long.parseLong(id);
        Category category = categoryRepository.findById(updateCategory).orElse(null);

        if(category != null){
            category.setCategoryName(data.getCategoryName());
            categoryRepository.save(category);
        }
    }

    public void deleteCategory(String id) {
        Long deleteId = Long.parseLong(id);
        categoryRepository.deleteById(deleteId);
    }
}
