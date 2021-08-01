package com.example.acko.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ProductControler {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/products")
	private List<Product> allProducts() {
		return productRepository.findAll();
		
	}

}
