package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	private List<Product> data = new ArrayList<Product>();
	private List<Product> dataSearch = new ArrayList<Product>();

	@GetMapping("/product")
	public List<Product> getProduct() {

		return productRepository.findAll();
	}

	@GetMapping("/product/{productId}")
	public Optional<Product> getProductDetail(@PathVariable Integer productId) {

		Optional<Product> product = productRepository.findById(productId);
		
		return product;
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product body) {

		return productRepository.save(body);
	}

	@PutMapping("/product/{productId}")
	public Product updateProduct(@PathVariable Integer productId, @RequestBody Product body) {

		Optional<Product> product = productRepository.findById(productId);
		
		if(product.isPresent()) {
			product.get().setProductName(body.getProductName());
			product.get().setProductPrice(body.getProductPrice());
			product.get().setProductDetail(body.getProductDetail());
			product.get().setProductAmount(body.getProductAmount());
			product.get().setProductId(body.getProductId());
			
			productRepository.save(product.get());
			
			return product.get();
		}
		else {
			return null;
		}
		
	}

	@DeleteMapping("/product/{productId}")
	public String deleteProduct(@PathVariable Integer productId) {

		Optional<Product> product = productRepository.findById(productId);
		
		if(product.isPresent()) {
			productRepository.delete(product.get());
			
			return "Delete Sucsess.";
		}
		else {
			return "Products Not Found.";
		}
		
	}

	@GetMapping("/product/search_text")
	public List<Product> searchProducts(@RequestParam String name) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductName().contains(name) || data.get(i).getProductDetail().contains(name)) {

				dataSearch.add(data.get(i));
			}

		}
		return dataSearch;
	}

}
