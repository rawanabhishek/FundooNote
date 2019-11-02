/******************************************************************************
 
 *  Purpose: A  Class implemented for handling the request coming from the user
 *           and Controlling it through RestController annotation using spring 
 *           boot that will handle all the request related to that user.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDTO;


import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.service.ILabelService;

@RestController
@RequestMapping("/user/label")
public class LabelController {
	
	
	@Autowired
	private ILabelService labelService;
	
	
	/**
	 * Purpose: Creating a Label controller for note to add labels which 
	 *          take request from request body and request header and 
	 *          send the response
	 * @param labelDTO  having label details 
	 * @param token containing user details 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PostMapping("/")
	public ResponseEntity<Response> add(@RequestBody LabelDTO labelDTO ,@RequestHeader("emailIdToken") String emailIdToken ){
		return new ResponseEntity<Response>(labelService.add(labelDTO ,emailIdToken),HttpStatus.OK);
		
	}
	
	/**
	 * Purpose:Creating a Label controller for note to get labels which 
	 *          take request from request header  and send the response
	 * @param labelIdToken token containing details of labelId
	 * @param emailIdToken containing emailId details
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@GetMapping("/")
	public ResponseEntity<Response> get(@RequestParam int labelId , @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<Response>(labelService.get(labelId, emailIdToken),HttpStatus.OK);
		
	}
	
	
	/**
	 * Purpose:Creating a Label controller for note to delete label  which 
	 *          take request from request header  and send the response
	 * @param labelIdToken token containing details of labelId
	 * @param emailIdToken containing emailId details
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@DeleteMapping("/")
	public ResponseEntity<Response> delete(@RequestParam int labelId , @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<Response>(labelService.delete(labelId, emailIdToken),HttpStatus.OK);
		
	}
	
	/**
	 * Purpose:Creating a Label controller for note to update labels  which 
	 *          take request from request body  and send the response
	 * @param labelUpdateDTO containing update label data 
	 * @param labelIdToken token containing details of labelId
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	
	@PutMapping("/")
	public ResponseEntity<Response> update(@RequestBody LabelDTO labelDTO ,@RequestParam("labelId") int labelId){
		return new ResponseEntity<Response>(labelService.update(labelDTO , labelId),HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
