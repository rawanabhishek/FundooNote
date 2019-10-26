package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.LabelUpdateDTO;

import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.service.ILabelService;

@RestController
@RequestMapping("/user/label")
public class LabelController {
	
	
	@Autowired
	private ILabelService labelService;
	
	
	/**
	 * Purpose: Creating a Label controller for note to add labels
	 * @param labelDTO
	 * @return
	 */
	@PostMapping("/")
	public ResponseEntity<Response> add(@RequestBody LabelDTO labelDTO){
		return new ResponseEntity<Response>(labelService.add(labelDTO),HttpStatus.OK);
		
	}
	
	/**
	 * Purpose:Creating a Label controller for note to get labels
	 * @param labelId
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<Response> get(@RequestHeader int labelId){
		return new ResponseEntity<Response>(labelService.get(labelId),HttpStatus.OK);
		
	}
	
	
	/**
	 * Purpose:Creating a Label controller for note to delete labels
	 * @param labelId
	 * @return
	 */
	@DeleteMapping("/")
	public ResponseEntity<Response> delete(@RequestHeader int labelId){
		return new ResponseEntity<Response>(labelService.delete(labelId),HttpStatus.OK);
		
	}
	
	/**
	 * Purpose:Creating a Label controller for note to update labels
	 * @param label
	 * @return
	 */
	@PutMapping("/")
	public ResponseEntity<Response> update(@RequestBody LabelUpdateDTO labelUpdateDTO){
		return new ResponseEntity<Response>(labelService.update(labelUpdateDTO),HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
