package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.service.ImplElasticSearchService;

@RestController
@RequestMapping("/elastic")
public class ElasticSearchcontroller {
	
	@Autowired
	private ImplElasticSearchService elasticService;
	
	
	@PostMapping("/add")
	public ResponseEntity<Response> addDocument(@RequestBody Note note) throws Exception {
		System.out.println("In controller");
		return new ResponseEntity<Response>((elasticService.addDocument(note)), HttpStatus.OK);
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> searById(@PathVariable String id) throws Exception {
		return new ResponseEntity<Response>((elasticService.readDocument(id)), HttpStatus.OK);
	}

	@GetMapping("/title")
	public ResponseEntity<Response> searchBytitle(@RequestParam String searchString) throws Exception {
		return new ResponseEntity<Response>((elasticService.search(searchString , "title")), HttpStatus.OK);
	}
	
	
	@GetMapping("/description")
	public ResponseEntity<Response> searchByDescription(@RequestParam String searchString) throws Exception {
		return new ResponseEntity<Response>((elasticService.search(searchString , "description")), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteDocument(@PathVariable String id) throws Exception {
		return new ResponseEntity<Response>((elasticService.deleteDocument(id)), HttpStatus.OK);
	}
	
	
	
	
	
	

}
