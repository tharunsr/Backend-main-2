package com.example.SS.service;

import com.example.SS.entities.Category;
import com.example.SS.exception.InvalidCategoryException;
import com.example.SS.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repo;

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category getCategoryById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void addCategory(Category prod) {
        if(prod.getName().length() < 2){
            throw new InvalidCategoryException("Name should be greater than 3 letters, Yours is : " + prod.getName());
        }
        repo.save(prod);
    }

    public void updateCategory(Category prod) {
        if(prod.getName().length() < 2){
            throw new InvalidCategoryException("Name should be greater than 3 letters, Yours is : " + prod.getName());
        }

        repo.save(prod);
    }

    public void deleteByCategoryId(int id) {
        repo.deleteById(id);
    }

    public Category findCategoryById(int id) {

        return repo.findById(id).orElse(null);
    }
}
