package com.example.devops.cicddemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.devops.cicddemo.entity.Product;
import com.example.devops.cicddemo.service.ProductService;



@ExtendWith(MockitoExtension.class) 
public class ProductControllerTest {
	

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;  
	
	@Mock
	Product product;
	    
	@Test
	void contextLoads() {
		
	}

	    @Test
	    public void testgetProduct()  {
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product(100, "Mobile", "CLK98123", 9000.00, 6));
	    	products.add(new Product(101, "Smart TV", "LGST09167", 60000.00, 3));
	    	products.add(new Product(102, "Washing Machine", "38753BK9", 9000.00, 7));
	    	products.add(new Product(103, "Laptop", "LHP29OCP", 24000.00, 1));
	    	products.add(new Product(104, "Air Conditioner", "ACLG66721", 30000.00, 5));
	    	products.add(new Product(105, "Refrigerator ", "12WP9087", 10000.00, 4));
	    	products.add(new Product(106, "Dishwasher ", "12WP9087", 10000.00, 4));
	    	products.add(new Product(107, "Charger ", "12WP9087", 10000.00, 4));
	    	products.add(new Product(108, "Charger ", "12WP9087", 10000.00, 4));
	    	products.add(new Product(110, "Charger ", "12WP9087", 10000.00, 4));

	    	
	    	when(productService.findAll()).thenReturn(products);
	    	products = productController.getProduct();
	    	assertThat(products.size()).isEqualTo(10);
	    }
	}

