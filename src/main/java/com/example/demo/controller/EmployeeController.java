package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;

@RestController
public class EmployeeController {

	private List<Employee> data = new ArrayList<Employee>();
	
	@GetMapping("employee")
	public List<Employee> getEmployee() {
		
		return data;
	}
	
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {
		
		for(int i = 0; i < data.size();i++) {
			if(data.get(i).getEmployeeId() == body.getEmployeeId()) {
				return null;
			}
		}
		
		data.add(body);
		return body;
	}
	
}
