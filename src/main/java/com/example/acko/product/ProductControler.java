package com.example.acko.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class ProductControler {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/categories")
	private ResponseEntity< List<Category>> allCategories() {
		List<Category> categories = productService.getAllCategories();
		return ResponseEntity.ok(categories);
		
	}
	
	@GetMapping("/products")
	private ResponseEntity< List<Product>> allProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/products/{id}")
	private ResponseEntity< List<Product>> allProductsByCategory(@PathVariable("id") Long id) {
		List<Product> products = productService.findByCategoryId(id);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/product/{id}")
	private ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping("/product/{id}")
	private ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody(required = false) Product product){
		Product updated = productService.updateProductById(id,product);
		return ResponseEntity.ok(updated);
	}

}
