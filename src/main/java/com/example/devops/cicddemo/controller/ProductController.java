package com.example.devops.cicddemo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devops.cicddemo.entity.Product;
import com.example.devops.cicddemo.service.IProductService;

@RestController
public class ProductController 
{
@Autowired
private IProductService iproductService;

@GetMapping(value = "/products")
public List<Product> getProduct() 
{
  List<Product> products = iproductService.findAll();
return products;
}
}
