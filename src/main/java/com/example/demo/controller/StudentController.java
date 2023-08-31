package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.Skill;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/students")
	public ResponseEntity<Object> getStudents() {

		try {
			List<Student> students = studentRepository.findAll();

			return new ResponseEntity<>(students, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> addStudents(@RequestBody Student body) {

		try {
			Student students = studentRepository.save(body);

			return new ResponseEntity<>(students, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/student/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Integer id, @RequestBody Student body) {

		try {

			Optional<Student> student = studentRepository.findById(id);

			if (student.isPresent()) {
				student.get().setFirstName(body.getFirstName());
				student.get().setLastName(body.getLastName());
				student.get().setEmail(body.getEmail());

				studentRepository.save(student.get());

				return new ResponseEntity<>(student, HttpStatus.OK);
			} 
			else 
			{
				return new ResponseEntity<>("student not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Integer id)
	{
		try {

			Optional<Student> student = studentRepository.findById(id);

			if (student.isPresent()) {
				studentRepository.delete(student.get());

				return new ResponseEntity<>("Delete successful", HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<>("student not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
