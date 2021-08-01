package com.example.acko.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Producer producer;
	
	@ManyToOne
	private Category category;
	
	private String name;
	
    private String description;
	
	private Double priceOnPack;
	
	private Double priceOnPiece;
	
    private String imageUrl;
	
	private Boolean isAvailable;
	
}
