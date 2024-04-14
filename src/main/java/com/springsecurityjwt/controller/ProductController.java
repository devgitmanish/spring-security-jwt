package com.springsecurityjwt.controller;

import com.springsecurityjwt.model.Products;
import com.springsecurityjwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/health")
    public String healthCheck(){
        return "product-service-up-and-running";
    }

    @GetMapping("/getProducts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Products> getAllProducts(){
        return productService.getProducts();
    }

    @GetMapping("/getProducts/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Products findProductByid(@PathVariable int id){
        return productService.productsById(id);
    }
}
