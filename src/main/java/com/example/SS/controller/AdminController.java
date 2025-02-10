package com.example.SS.controller;


import com.example.SS.dto.CategoryDto;
import com.example.SS.dto.ProductDto;
import com.example.SS.entities.Category;
import com.example.SS.entities.Product;
import com.example.SS.exception.InvalidCategoryException;
import com.example.SS.service.CategoryService;
import com.example.SS.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService catService;

    @Autowired
    private ProductService proService;


//    -------------------------------CATEGORY

    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto catDto) {
        try {
            Category cat =  modelMapper.map(catDto,Category.class);
            catService.addCategory(cat);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully!");
        } catch (InvalidCategoryException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check Database or enter details");
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody CategoryDto catDto) {
        try {
            Category existingCategory = catService.findCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }

            modelMapper.map(catDto,existingCategory);
            existingCategory.setId(id);
            catService.updateCategory(existingCategory);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category updated successfully!");
        }
        catch (InvalidCategoryException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check Database or enter details");
        }

    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteByCategoryId(@PathVariable int id) {
        try {
            catService.deleteByCategoryId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Caution! PK is used");
        }


    }

    //    ------------------------------- PRODUCT
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto prod) {
        try {
            Product product = modelMapper.map(prod,Product.class);
            proService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Is either Category id correct or present");
        }

    }

    @PutMapping("products/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id,
            @RequestBody ProductDto prod) {
        try {
            System.out.println("Received request body: " + prod);
            // Check if the product exists in the database
            Product existingProduct = proService.findProductById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            // Map the incoming DTO to the existing product entity
            modelMapper.map(prod, existingProduct);
            existingProduct.setId(id);
            // Save the updated product
            proService.updateProduct(existingProduct);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteByProductId(@PathVariable int id) {
        try {
            proService.deleteByProductId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Caution! PK is used");
        }


    }
//   ----------------------------------CUSTOMER


}
