package com.example.acko;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.acko.product.Category;
import com.example.acko.product.CategoryRepository;
import com.example.acko.product.Producer;
import com.example.acko.product.ProducerRepository;
import com.example.acko.product.Product;
import com.example.acko.product.ProductRepository;
import com.example.acko.user.Role;
import com.example.acko.user.RoleName;
import com.example.acko.user.RoleRepository;

@SpringBootApplication
@EnableConfigurationProperties
public class AckoApplication {
	
	private final String folderUrl = "/src/main/resources/static/images/";
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProducerRepository producerRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(AckoApplication.class, args);
		
	}
	@PostConstruct
	public void init() {
	try {
		
		
		roleRepository.save(new Role(1L, RoleName.ROLE_ADMIN));
		roleRepository.save(new Role(2L, RoleName.ROLE_USER));
		
		producerRepository.save(new Producer(1L, "MIRKA",folderUrl+"mirka.png"));
		producerRepository.save(new Producer(2L, "DeBeer",folderUrl+"debeer.png"));
		producerRepository.save(new Producer(3L, "Spralac",folderUrl+"spralac.png"));
		producerRepository.save(new Producer(4L, "FINIXA",folderUrl+"finixa.png"));
		producerRepository.save(new Producer(5L, "SATA",folderUrl+"sata.png"));
		producerRepository.save(new Producer(6L, "Spiralflex",folderUrl+"spiralflex.png"));
		
		categoryRepository.save(new Category(1L, "Tools",folderUrl+"deros.jfif"));
		categoryRepository.save(new Category(2L, "Abrasives",folderUrl+"discs.jfif"));
		categoryRepository.save(new Category(3L, "Coats",folderUrl+"8-214.jfif"));
		categoryRepository.save(new Category(4L, "Hardeners",folderUrl+"8-150.jfif"));
		categoryRepository.save(new Category(5L, "Thinners",folderUrl+"1-151.jfif"));
		categoryRepository.save(new Category(6L, "Primers",folderUrl+"8-14510.jfif"));
		categoryRepository.save(new Category(7L, "Helpers",folderUrl+"help.jfif"));
		categoryRepository.save(new Category(8L, "Putties",folderUrl+"1-909.jfif"));
		
		productRepository.save(new Product(1L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "8-104" , "HS , 2:1 " , 3500.00 , 760.00 ,folderUrl+ "8-104.jfif" , true));
		productRepository.save(new Product(2L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "8-214" , "Antiscrach HS , 2:1 " , 4200.00 , 950.00 ,folderUrl+ "8-214.jfif" , true));
		productRepository.save(new Product(3L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "8-614" , "Ultra HS , 3:1 " , 5000.00 , 1150.00 ,folderUrl+ "214.jfif" , true));
		productRepository.save(new Product(4L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "8-114" , "HS , 5:1 " , 5500.00 , 1200.00 ,folderUrl+ "214.jfif" , true));
		productRepository.save(new Product(5L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "1-103" , "2k , 2:1 " , 2750.00 , 600.00 ,folderUrl+ "214.jfif" , true));
		productRepository.save(new Product(6L, producerRepository.findById(2L).get(), categoryRepository.findById(3L).get() , "1-204" , "MS , 5:1 " , 3000.00 , 680.00 ,folderUrl+ "214.jfif" , true));
		productRepository.save(new Product(7L, producerRepository.findById(3L).get(), categoryRepository.findById(3L).get() , "4699" , "MS, 2:1 " , 2200.00 , 490.00 ,folderUrl+ "4699.jfif" , true));
		productRepository.save(new Product(8L, producerRepository.findById(3L).get(), categoryRepository.findById(3L).get() , "4501" , "HS , 2:1 " , 2500.00 , 550.00 ,folderUrl+ "4501.jfif" , true));
		productRepository.save(new Product(9L, producerRepository.findById(3L).get(), categoryRepository.findById(3L).get() , "4502" , "Antiscrach HS , 3:1 " , 3500.00 , 750.00 ,folderUrl+ "4501.jfif" , true));
		
		
		
		
		
	} catch (Exception e) {
		System.err.println(e);
	}

	}
}


