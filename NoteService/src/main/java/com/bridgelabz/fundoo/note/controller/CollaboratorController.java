/******************************************************************************
 
 *  Purpose: A  Class implemented for handling the request coming from the user
 *           and Controlling it through RestController annotation using spring 
 *           boot that will handle all the request related to that user.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.service.ICollaboratorService;


@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {
	
	@Autowired
	ICollaboratorService service;
	/**
	 * Purpose: Creating a Collaborator controller to add collaborators 
	 * @param token containing user details 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PostMapping("/")
	public ResponseEntity<Response> add(@RequestHeader String  emailIdToken ){
		return new ResponseEntity<Response>(service.add(emailIdToken),HttpStatus.OK);
	}
	
	
	
	/**
	 * Purpose: Creating a Collaborator controller to delete collaborators 
	 *       
	 * @param token containing collaborator email details 
	 * @param collaboratorId of particular collaborator
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@DeleteMapping("/")
	public ResponseEntity<Response> delete(@RequestHeader String emailIdToken ,@RequestParam int collaboratorId){
		return new ResponseEntity<Response>(service.delete(emailIdToken, collaboratorId), HttpStatus.OK);
	}
	
	
	
	/**
	 * Purpose: Creating a Collaborator controller to get  collaborator which 
	 *         
	 * @param token containing collaborator email details 
	 * @param collaboratorId of particular collaborator
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@GetMapping("/")
	public ResponseEntity<Response> get(@RequestHeader String emailIdToken,@RequestParam int collaboratorId){
		return new ResponseEntity<Response>(service.get( emailIdToken,collaboratorId), HttpStatus.OK);
	}
	
	
	
	
		
		
	

}
