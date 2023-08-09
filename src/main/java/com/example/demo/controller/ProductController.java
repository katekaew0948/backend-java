package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> getProduct() {
		
		try {
			
			return new ResponseEntity<>(productRepository.findAll() , HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Object> getProductDetail(@PathVariable Integer productId) {
		
		try {
			
			Optional<Product> product = productRepository.findById(productId);
			
			if(product.isPresent()) {
				
				return new ResponseEntity<>(product , HttpStatus.OK);
			}
			else {
				
				return new ResponseEntity<>("Product not found" , HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (Exception e) {
			
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/product")
	public ResponseEntity<Object> addProduct(@RequestBody Product body) {
		
		try {
			
			return new ResponseEntity<>(productRepository.save(body) , HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/product/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable Integer productId, @RequestBody Product body) {

		try {
			
			Optional<Product> product = productRepository.findById(productId);
			
			if(product.isPresent()) {
				product.get().setProductName(body.getProductName());
				product.get().setProductPrice(body.getProductPrice());
				product.get().setProductDetail(body.getProductDetail());
				product.get().setProductAmount(body.getProductAmount());
				product.get().setProductId(body.getProductId());
				
				productRepository.save(product.get());
				
				return new ResponseEntity<>(product.get() , HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Product not found" , HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Integer productId) {
		
		try {
			
			Optional<Product> product = productRepository.findById(productId);
			
			if(product.isPresent()) {
				productRepository.delete(product.get());
				
				return new ResponseEntity<> ("Delete Sucsess." ,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object> ("Products Not Found." , HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
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
