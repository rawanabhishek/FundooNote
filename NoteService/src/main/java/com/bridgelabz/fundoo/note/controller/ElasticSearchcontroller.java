/******************************************************************************
 
 *  Purpose: A  Class implemented for handling the request coming from the user
 *           and Controlling it through RestController annotation using spring 
 *           boot that will handle all the request related to that user.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   02-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("user/elastic")
@CrossOrigin
public class ElasticSearchcontroller {
	
	@Autowired
	private ImplElasticSearchService elasticService;
	
	
	/**
	 * Purpose: To add document in elastic search engine
	 * @param note the document which we need to add to the elastic search
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@PostMapping("/add")
	public ResponseEntity<Response> addDocument(@RequestBody Note note) throws Exception {
		System.out.println("In controller");
		return new ResponseEntity<Response>((elasticService.addDocument(note)), HttpStatus.OK);
		
	}

	/**
	 * Purpose: To search a document by id from elastic search
	 * @param id of document which is needed to be searched
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Response> searById(@PathVariable String id) throws Exception {
		return new ResponseEntity<Response>((elasticService.readDocument(id)), HttpStatus.OK);
	}

	/**
	 * Purpose: To search document by title from the elastic search
	 * @param searchString containing value which is needed to be searched
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@GetMapping("/title")
	public ResponseEntity<Response> searchBytitle(@RequestParam String searchString) throws Exception {
		return new ResponseEntity<Response>((elasticService.search(searchString , "title")), HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: To search document by description from the elastic search
	 * @param searchString containing value which is needed to be searched
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@GetMapping("/description")
	public ResponseEntity<Response> searchByDescription(@RequestParam String searchString) throws Exception {
		return new ResponseEntity<Response>((elasticService.search(searchString , "description")), HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: to delete a document from the elastic search
	 * @param id of the document which is needed to be delete
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteDocument(@PathVariable String id) throws Exception {
		return new ResponseEntity<Response>((elasticService.deleteDocument(id)), HttpStatus.OK);
	}
	
	/**
	 * Purpose:  To search document by title and description from the elastic search
	 * @param searchString containing value which is needed to be searched
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * @throws Exception
	 */
	@GetMapping("/title/description")
	public ResponseEntity<Response> searchByTitleDescription(@RequestParam String searchString) throws Exception {
		return new ResponseEntity<Response>((elasticService.searchByTitleDescription(searchString )), HttpStatus.OK);
	}
	
	
	
	
	
	

}
