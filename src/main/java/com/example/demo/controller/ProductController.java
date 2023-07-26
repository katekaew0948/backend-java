package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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

@RestController
public class ProductController {

	@Autowired
	
	
	private List<Product> data = new ArrayList<Product>();
	private List<Product> dataSearch = new ArrayList<Product>();

	@GetMapping("product")
	public List<Product> getProduct() {

		return data;
	}

	@GetMapping("/product/{productId}")
	public Product getProductDetail(@PathVariable Integer productId) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == productId) {

				return data.get(i);
			}
		}
		return null;
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product body) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == body.getProductId()) {

				return null;
			}
		}

		data.add(body);
		return body;
	}

	@PutMapping("/product/{productId}")
	public Product updateProduct(@PathVariable Integer productId, @RequestBody Product body) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == productId) {

				data.get(i).setProductName(body.getProductName());
				data.get(i).setProductPrice(body.getProductPrice());
				data.get(i).setProductDetail(body.getProductDetail());
				data.get(i).setProductAmount(body.getProductAmount());
				return data.get(i);
			}
		}

		return null;
	}

	@DeleteMapping("/product/{productId}")
	public String deleteProduct(@PathVariable Integer productId) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == productId) {

				data.remove(i);

				return "Delete success";
			}
		}

		return "Product not found";
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
