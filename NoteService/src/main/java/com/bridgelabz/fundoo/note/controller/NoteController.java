/******************************************************************************
 
 *  Purpose: A  Class implemented for handling the request coming from the user
 *           and Controlling it through RestController annotation using spring 
 *           boot that will handle all the request related to that user.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.controller;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.NoteUpdateDTO;

import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.service.INoteService;


@RestController
@RequestMapping("/user/note")
public class NoteController {
	
	
	@Autowired
	private INoteService noteService;
	
	public static final Logger LOG = LoggerFactory.getLogger(NoteController.class);
	
	
	/**
	 * Purpose: Creating a addNote controller which will fetch the request body
	 * and send it to the service.
	 * @param noteDTO object containing user addNote details
	 * @param token containing user details 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * 
	 */
	@PostMapping("/")
	public ResponseEntity<Response> add(@RequestBody NoteDTO noteDTO ,@RequestHeader("emailIdToken") String emailIdToken) {
		
		return new ResponseEntity<>(noteService.add(noteDTO ,emailIdToken),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating a getNote controller which will fetch the header
	 * and send it to the service.
	 * @param emailIdToken token containing email id 
	 * 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * 
	 */
	@GetMapping("/")
	public ResponseEntity<Response> get(@RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.get(emailIdToken),HttpStatus.OK);
	}
	
	
	/**
	 *  Purpose: Creating a updateNote controller which will fetch the request body
	 * @param updateDTO containing new update data for a particular note 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 * 
	 */
	@PutMapping("/")
	public ResponseEntity<Response> update(@RequestBody NoteUpdateDTO updateDTO , 
			@RequestParam("noteId") int noteId ,@RequestHeader("emailIdToken") String emailIdToken) {
		
		return new ResponseEntity<>(noteService.update(updateDTO, noteId , emailIdToken),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a deleteNote controller which will fetch the header
	 * @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@DeleteMapping("/")
	public ResponseEntity<Response> delete(@RequestParam("noteId") int  noteId , 
			@RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.delete(noteId ,emailIdToken),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a pin controller which will fetch the header
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/pin")
	public ResponseEntity<Response> pin(@RequestParam("noteId")int  noteId , @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.pin(noteId ,emailIdToken),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a archive controller which will fetch the header
	 * @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/archive")
	public ResponseEntity<Response> archive(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.archive(noteId ,emailIdToken),HttpStatus.OK);
	}
	
	/**
	 *  Purpose: Creating a archive controller which will fetch the header
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/archivepin")
	public ResponseEntity<Response> archivePin(@RequestParam("noteId") int noteId, @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.archivePin(noteId ,emailIdToken),HttpStatus.OK);
	}
	
	
	
	/**
	 * Purpose: Creating a trash controller which will fetch the header
	 * @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/trash")
	public ResponseEntity<Response> trash(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.trash(noteId ,emailIdToken),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating sortDate controller which fetch the header
	 * @param emailIdToken token containing email id 
	 * 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@GetMapping("/date")
	public ResponseEntity<Response> sortDate(@RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.sortDate(emailIdToken),HttpStatus.OK);
	}
	
	/**
	 * Purpose: Creating sortName controller which fetch the header
	 * @param emailIdToken token containing email id 
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@GetMapping("/name")
	public ResponseEntity<Response> sortName(@RequestHeader("emailIdToken") String emailIdToken){
		return new ResponseEntity<>(noteService.sortName(emailIdToken),HttpStatus.OK);
	}
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param labelId
	 * @return
	 */
	@PutMapping("/label")
	public ResponseEntity<Response> addLabel(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestHeader("labelId") int labelId){
		
		return new ResponseEntity<>(noteService.addLabel(noteId ,emailIdToken ,labelId),HttpStatus.OK);
	}
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param labelId
	 * @return
	 */
	@PutMapping("/removelabel")
	public ResponseEntity<Response> removeLabel(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestParam("labelId") int labelId){
		
		return new ResponseEntity<>(noteService.removeLabel(noteId ,emailIdToken ,labelId),HttpStatus.OK);
	}
	
//	@PutMapping("/addcollaborator")
//	public ResponseEntity<Response> addCollaborator(@RequestParam int  noteId , @RequestHeader
//			String emailIdToken , @RequestHeader String collaborator){
//		System.out.println("controller delete label");
//		return new ResponseEntity<>(noteService.addCollaborator(noteId ,emailIdToken , collaborator),HttpStatus.OK);
//	}
//	
//	
//	@PutMapping("/removecollaborator")
//	public ResponseEntity<Response> removeCollaborator(@RequestParam int  noteId , @RequestHeader
//			String emailIdToken , @RequestHeader String collaborator){
//		System.out.println("controller delete label");
//		return new ResponseEntity<>(noteService.removeCollaborator(noteId ,emailIdToken , collaborator),HttpStatus.OK);
//	}
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param date
	 * @return
	 */
	@PutMapping("/addremainder")
	public ResponseEntity<Response> addRemainder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , 
			@RequestParam("reminder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date date){
		
		return new ResponseEntity<>(noteService.addReminder(noteId ,emailIdToken , date),HttpStatus.OK);
	}
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param date
	 * @return
	 */
	@PutMapping("/updateremainder")
	public ResponseEntity<Response> updateRemainder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestParam("reminder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date date){
	
		return new ResponseEntity<>(noteService.updateReminder(noteId ,emailIdToken , date),HttpStatus.OK);
	}
	
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @return
	 */
	@PutMapping("/removeremainder")
	public ResponseEntity<Response> removeRemainder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken ){
		
		return new ResponseEntity<>(noteService.removeReminder(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param color
	 * @return
	 */
	@PutMapping("/addcolor")
	public ResponseEntity<Response> addColor(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken  ,@RequestParam("color")String color){
		
		return new ResponseEntity<>(noteService.removeReminder(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	@PutMapping("/removecolor")
	public ResponseEntity<Response> removeColor(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken ){
		
		return new ResponseEntity<>(noteService.removeColor(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	
	/**
	 * @param noteId
	 * @param emailIdToken
	 * @param file
	 * @return
	 */
	@PutMapping("/addImage")
	public ResponseEntity<Response> addImage(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken  , @RequestParam MultipartFile file){
		
		return new ResponseEntity<>(noteService.addImage(noteId ,emailIdToken , file),HttpStatus.OK);
	}
	
	
	
	

}
