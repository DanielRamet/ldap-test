package com.example.drm.ldaptest.controller;

import com.example.drm.ldaptest.model.Product;
import com.example.drm.ldaptest.model.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private List<Product> products = new ArrayList<>();

    @GetMapping("/products")
    public List<Product> getProductList() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println("USERNAME: " + username);
        return products;
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable(value = "productId") Long productId) {
        return this.products.stream().filter(p -> p.id == productId).findFirst().orElseThrow(() -> new ResourceNotFoundException("productId " + productId + " not found"));
    }

    @PostMapping("/products")
    public String createProduct(@RequestBody Product product) {
        this.products.add(product);
        return "Product added";
    }
}
