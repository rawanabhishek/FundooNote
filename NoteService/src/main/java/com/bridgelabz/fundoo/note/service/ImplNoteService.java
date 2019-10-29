/******************************************************************************
 
 *  Purpose: This is a service class for NoteService implementing noteService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.NoteUpdateDTO;
import com.bridgelabz.fundoo.note.exception.custom.NoteException;
import com.bridgelabz.fundoo.note.exception.custom.LabelException;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;
import com.bridgelabz.fundoo.note.utility.TokenUtility;



@Service
public class ImplNoteService implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);

	
	/**
	 * Purpose: Method for adding new note for user 
	 * @param noteDTO containing note data which will  be later map to the note 
	 *        model .
	 * @param token containing user details for which the particular note will
	 *        be created . To read the data of the token we are parsing the 
	 *        token and getting the user data.
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response add(NoteDTO noteDTO ,String token ) {

		LOG.info(CommonFiles.SERVICE_ADD_METHOD);
		if ((noteDTO.getTitle().isEmpty() || noteDTO.getDescription().isEmpty())) {

			throw new NoteException(CommonFiles.ADD_NOTE_FAILED);

		}
		String key=TokenUtility.tokenParser(token);
		
        
		Note note = configuration.modelMapper().map(noteDTO, Note.class);
         note.setUser(key);
		return new Response(200, CommonFiles.ADD_NOTE_SUCCESS, noteRepository.save(note));

	}

	
	/**
	 * Purpose: Method for updating notes of a particular user 
	 * @param updateDTO containing the updated data for a particular note
	 *        and setting its value to model and saving it to the
	 *        database.
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response update(NoteUpdateDTO updateDTO) {
		LOG.info(CommonFiles.SERVICE_UPDATE_METHOD);

		if (!(updateDTO.getTitle().isEmpty() || updateDTO.getDescription().isEmpty())) {

			Note note = noteRepository.findById(updateDTO.getNoteId()).orElse(null);
			note.setDescription(updateDTO.getDescription());
			note.setTitle(updateDTO.getTitle());
			return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS, noteRepository.save(note));
		} else {

			throw new NoteException(CommonFiles.UPDATE_NOTE_FAILED);
		}
	}

	
	/**
	 * Purpose:Method for deleting notes of a particular user 
	 * @param noteId of a note which we need to delete it from the
	 *        database
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response delete(int noteId) {
		
		LOG.info(CommonFiles.SERVICE_DELETE_METHOD);
		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null) {
			throw new LabelException(CommonFiles.DELETE_NOTE_FAILED);
		}
		noteRepository.deleteById(noteId);
		return new Response(200, CommonFiles.DELETE_NOTE_SUCCESS, true);
	}

	
	/**
	 * Purpose: Method for getting all the note of a given user
	 * @param userId for which we have to fetch all the note
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response get(String user) {
		LOG.info(CommonFiles.SERVICE_GET_METHOD);

		Stream<Note> note = noteRepository.findAll().stream().filter(i -> i.getUser().equals(user) );

		if (note == null) {
			throw new NoteException(CommonFiles.GET_NOTE_FAILED);
		}

		return new Response(200, CommonFiles.GET_NOTE_SUCCESS, note);
	}

	
	/**
	 * Purpose: Method for pin and unpin note
	 * @param id of note which we need to pin or unpin 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response pin(int id) {
		
		LOG.info(CommonFiles.SERVICE_PIN_METHOD);
		Note note = noteRepository.findById(id).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
		if (note.isPin()) {
			note.setPin(false);
		} else {
			note.setPin(true);
		}

		return new Response(200, CommonFiles.PIN_SUCCESS, noteRepository.save(note));
		}
	}

	
	/**
	 * Purpose: Method for archive and unarchive a note
	 * @param id of note which we need to archive or unarchive
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response archive(int id) {
		LOG.info(CommonFiles.SERVICE_ARCHIVE_METHOD);
		Note note = noteRepository.findById(id).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isArchive()) {
				note.setArchive(false);
			} else {
				note.setArchive(true);
			}

			return new Response(200, CommonFiles.ARCHIVE_SUCCESS, noteRepository.save(note));

		}
	}

	
	
	/**
	 * Purpose:Method for trash and untrash a note
	 * @param id of note which we need to trash or untrash
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response trash(int id) {
		LOG.info(CommonFiles.SERVICE_TRASH_METHOD);
		
		Note note = noteRepository.findById(id).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isTrash()) {
				note.setTrash(false);
			} else {
				note.setTrash(true);
			}

			return new Response(200, CommonFiles.TRASH_SUCCESS, noteRepository.save(note));
		}

	}

	
	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * @param user for which we have to sort all the notes of that user
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response sortDate(String user) {
		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		Stream<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getUser().equals(user) )
				.sorted((Note n1, Note n2) -> n1.getUpdate().compareTo(n2.getUpdate())).parallel();

		return new Response(200, CommonFiles.SORT_DATE_SUCCESS, sortedNote);

	}

	
	/**
	 * Purpose: Method for sorting notes of a user by name (title()  
	 * @param user for which we have to sort all the notes of that user
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response sortName(String user) {
		
		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		Stream<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getUser().equals(user))
				.sorted((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle())).parallel();

		return new Response(200, CommonFiles.SORT_NAME_SUCCESS, sortedNote);
	}

}
