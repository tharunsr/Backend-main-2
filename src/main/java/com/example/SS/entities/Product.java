package com.example.SS.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;

    @ManyToMany(mappedBy = "product")
    Set<Person> customer = new HashSet<>();

//    public Set<Customer> getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Set<Customer> customer) {
//        this.customer = customer;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
