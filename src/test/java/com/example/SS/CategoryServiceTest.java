package com.example.SS;

import com.example.SS.entities.Category;
import com.example.SS.exception.InvalidCategoryException;
import com.example.SS.repository.CategoryRepository;
import com.example.SS.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1);
        category.setName("Haircare");
    }


    @Test
    void testGetCategoryById() {
        when(repo.findById(1)).thenReturn(Optional.of(category));

        Category result = service.getCategoryById(1);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
    }

    @Test
    void testAddCategory() {
        Category newCategory = new Category();
        newCategory.setName("Bodycare");

        service.addCategory(newCategory);
      

        verify(repo, times(1)).save(newCategory);
    }


    @Test
    void testUpdateCategory() {
        category.setName("Appliances");

        service.updateCategory(category);

        verify(repo, times(1)).save(category);
    }


    @Test
    void testDeleteByCategoryId() {
        service.deleteByCategoryId(3);

        verify(repo, times(1)).deleteById(3);
    }
}
