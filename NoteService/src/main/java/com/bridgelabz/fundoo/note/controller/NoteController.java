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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.UpdateDTO;
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
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public ResponseEntity<Response> addNote(@RequestBody NoteDTO noteDTO) {
		LOG.info("inside add note controller");
		return new ResponseEntity<>(noteService.addNote(noteDTO),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating a getNote controller which will fetch the header
	 * and send it to the service.
	 * @param userId  containing user id.
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public ResponseEntity<Response> getNote(@RequestHeader int userId){
		return new ResponseEntity<>(noteService.readNote(userId),HttpStatus.OK);
	}
	
	
	/**
	 *  Purpose: Creating a updateNote controller which will fetch the request body
	 * @param updateDTO
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateDTO updateDTO) {
		
		return new ResponseEntity<>(noteService.updateNote(updateDTO),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a deleteNote controller which will fetch the header
	 * @param id
	 * @return
	 */
	@DeleteMapping("/")
	public ResponseEntity<Response> deleteNote(@RequestHeader int id){
		return new ResponseEntity<>(noteService.deleteNote(id),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a pin controller which will fetch the header
	 * @param id
	 * @return
	 */
	@PutMapping("/pin")
	public ResponseEntity<Response> pin(@RequestHeader int id){
		return new ResponseEntity<>(noteService.pin(id),HttpStatus.OK);
	}
	

	/**
	 *  Purpose: Creating a archive controller which will fetch the header
	 * @param id
	 * @return
	 */
	@PutMapping("/archive")
	public ResponseEntity<Response> archive(@RequestHeader int id){
		return new ResponseEntity<>(noteService.archive(id),HttpStatus.OK);
	}
	
	
	
	/**
	 *  Purpose: Creating a trash controller which will fetch the header
	 * @param id
	 * @return
	 */
	@PutMapping("/trash")
	public ResponseEntity<Response> trash(@RequestHeader int id){
		return new ResponseEntity<>(noteService.trash(id),HttpStatus.OK);
	}

}
