package com.example.SS.service;

import com.example.SS.entities.Category;
import com.example.SS.entities.Product;
import com.example.SS.repository.CategoryRepository;
import com.example.SS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private CategoryRepository catRepo;


    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void addProduct(Product prod) throws Exception {

                repo.save(prod);
    }

    public void updateProduct(Product prod) throws Exception {

        repo.save(prod);
    }

    public void deleteByProductId(int id) {
        repo.deleteById(id);
    }


}


