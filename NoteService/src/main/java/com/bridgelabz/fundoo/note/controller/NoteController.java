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
	 * @param noteId  containing note id
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
	 * @param noteId containing note id
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
	 * @param noteId containing note id
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
	 * @param noteId containing note id
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
	 * @param noteId containing note id
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
	 * @param noteId containing note id
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
	 * Purpose: Creating add Label controller for adding label to the note
	 * @param noteId containing note id
	 * @param emailIdToken token containing email id 
	 * @param labelId containing label id 
	 * @returnResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/label")
	public ResponseEntity<Response> addLabel(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestHeader("labelId") int labelId){
		
		return new ResponseEntity<>(noteService.addLabel(noteId ,emailIdToken ,labelId),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating remove Label controller for removing label from the note
	 * @param noteId  containing note id
	 * @param emailIdToken token containing email id 
	 * @param labelId containing label id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/removelabel")
	public ResponseEntity<Response> removeLabel(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestParam("labelId") int labelId){
		
		return new ResponseEntity<>(noteService.removeLabel(noteId ,emailIdToken ,labelId),HttpStatus.OK);
	}
	
	@PutMapping("/addcollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam int  noteId , @RequestHeader
			String emailIdToken , @RequestHeader String collaborator){
		
		return new ResponseEntity<>(noteService.addCollaborator(noteId ,emailIdToken , collaborator),HttpStatus.OK);
	}
	
	
	@PutMapping("/removecollaborator")
	public ResponseEntity<Response> removeCollaborator(@RequestParam int  noteId , @RequestHeader
			String emailIdToken , @RequestHeader String collaborator){
		
		return new ResponseEntity<>(noteService.removeCollaborator(noteId ,emailIdToken , collaborator),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating add Reminder controller which will addreminder to the note
	 * @param noteId containing note id
	 * @param emailIdToken token containing email id
	 * @param date for setting the reminder date
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PostMapping("/addreminder")
	public ResponseEntity<Response> addReminder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , 
			@RequestParam("reminder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date date){
		
		return new ResponseEntity<>(noteService.addReminder(noteId ,emailIdToken , date),HttpStatus.OK);
	}
	
	/**
	 * Purpose: Creating update reminder which will update the reminder in the note
	 * @param noteId containing note id
	 * @param emailIdToken token containing email id
	 * @param date for updating reminder
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/updatereminder")
	public ResponseEntity<Response> updateReminder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken , @RequestParam("reminder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date date){
	
		return new ResponseEntity<>(noteService.updateReminder(noteId ,emailIdToken , date),HttpStatus.OK);
	}
	
	
	
	/**
	 * Purpose: Creating remove reminder controller which will remove reminder from note
	 * @param noteId containing note id
	 * @param emailIdToken containing email id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/removeremainder")
	public ResponseEntity<Response> removeRemainder(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken ){
		
		return new ResponseEntity<>(noteService.removeReminder(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating add color controller which will add color color to the note
	 * @param noteId containing note id
	 * @param emailIdToken containing email id
	 * @param color code for a note in hex color format
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PostMapping("/addcolor")
	public ResponseEntity<Response> addColor(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken  ,@RequestParam("color")String color){
		
		return new ResponseEntity<>(noteService.removeReminder(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	/**
	 * Purpose: Creating remove color controller which will remove color from note
	 * @param noteId containing note id
	 * @param emailIdToken containing email id
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/removecolor")
	public ResponseEntity<Response> removeColor(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken ){
		
		return new ResponseEntity<>(noteService.removeColor(noteId ,emailIdToken ),HttpStatus.OK);
	}
	
	
	/**
	 * vPurpose: Creating update color controller which will update color for note
	 * @param noteId containing note id
	 * @param emailIdToken containing email id
	 * @param color code for a note in hex color format
	 * @return ResponseEntity containing Response which contains status code,
	 *         message and object
	 */
	@PutMapping("/updatecolor")
	public ResponseEntity<Response> updateColor(@RequestParam("noteId") int  noteId , @RequestHeader("emailIdToken")
			String emailIdToken ,@RequestParam("color")String color ){
		
		return new ResponseEntity<>(noteService.updateColor(noteId ,emailIdToken,color ),HttpStatus.OK);
	}
	
	
	
	
	

	
	
	
	

}
