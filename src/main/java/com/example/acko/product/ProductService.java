package com.example.acko.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
		
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public List<Product> findByCategoryId(Long id) {
		Category category = categoryRepository.findById(id).get();
		List<Product> products = productRepository.findByCategoryId(category.getId());
		return products;
	}

	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).get();
	}

	public Product updateProductById(Long id, Product product) {
		Product updated = productRepository.findById(id).get();
		updated.setCategory(product.getCategory());
		updated.setDescription(product.getDescription());
		updated.setIsAvailable(product.getIsAvailable());
		updated.setName(product.getName());
		updated.setPriceOnPack(product.getPriceOnPack());
		updated.setPriceOnPiece(product.getPriceOnPiece());
		
		return updated;
	}

}
