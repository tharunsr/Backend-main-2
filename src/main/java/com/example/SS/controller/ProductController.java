package com.example.SS.controller;

import com.example.SS.dto.ProductDto;
import com.example.SS.entities.Product;
import com.example.SS.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ModelMapper modelMapper;

//    @GetMapping(" ")
//    public String display(HttpServletRequest req){
//        return "Welcome to Cosmetics " + req.getSession().getId();
//    }


    @GetMapping("/products")
    public List<ProductDto> sortProducts(@RequestParam(required = false) String name){
        List<Product> products = (name !=null && !name.isEmpty())?
                service.getByName(name)
                :service.getAll() ;
        return products.stream()
                .map(prod -> modelMapper.map(prod,ProductDto.class))
                .toList();
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable int id){
        Product product = service.getProductById(id);
        return modelMapper.map(product,ProductDto.class);
    }

//    @PostMapping("/products")
//    public void addProduct(@RequestBody Product prod){
//        service.addProduct(prod);
//    }
//
//    @PutMapping("/products")
//    public void updateProduct(@RequestBody Product prod){
//        service.updateProduct(prod);
//    }
//
//    @DeleteMapping("/products/{id}")
//    public void deleteByProductId(@PathVariable int id){
//        service.deleteByProductId(id);
//    }



}
