package com.example.SS.controller;

import com.example.SS.dto.CategoryDto;
import com.example.SS.entities.Category;
import com.example.SS.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public List<CategoryDto> getAll(){
        List<Category> category = service.getAll();
        return category.stream()
                .map(cat -> modelmapper.map(cat,CategoryDto.class))
                .toList();
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategoryById(@PathVariable int id){
        Category cat = service.getCategoryById(id);
        return modelmapper.map(cat,CategoryDto.class);
    }


}
