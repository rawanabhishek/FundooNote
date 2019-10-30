/******************************************************************************
 
 *  Purpose: This is a service class for NoteService implementing noteService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import java.util.List;
import java.util.stream.Collectors;



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
	 * 
	 * @param noteDTO containing note data which will be later map to the note model
	 *                .
	 * @param token   containing user details for which the particular note will be
	 *                created . To read the data of the token we are parsing the
	 *                token and getting the user data.
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response add(NoteDTO noteDTO, String token) {

		LOG.info(CommonFiles.SERVICE_ADD_METHOD);
		if (!(noteDTO.getTitle().isEmpty() || noteDTO.getDescription().isEmpty())) {

			throw new NoteException(CommonFiles.ADD_NOTE_FAILED);

		}
		String key = TokenUtility.tokenParser(token);

		Note note = configuration.modelMapper().map(noteDTO, Note.class);
		note.setEmailId(key);

		noteRepository.save(note);
		String noteToken = TokenUtility.tokenBuild(note.getNoteId().toString());
		return new Response(200, CommonFiles.ADD_NOTE_SUCCESS, noteToken);

	}

	/**
	 * Purpose: Method for updating notes of a particular user
	 * 
	 * @param updateDTO containing the updated data for a particular note and
	 *                  setting its value to model and saving it to the database.
	 * @param emailIdToken token containing email id 
	 *                  
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response update(NoteUpdateDTO updateDTO, String noteIdToken) {
		LOG.info(CommonFiles.SERVICE_UPDATE_METHOD);
		int noteId = TokenUtility.tokenParserInt(noteIdToken);

		Note note = noteRepository.findById(noteId).orElse(null);
		if ((!(updateDTO.getTitle().isEmpty() || updateDTO.getDescription().isEmpty())) && note == null) {
			throw new NoteException(CommonFiles.UPDATE_NOTE_FAILED);
		}

		note.setDescription(updateDTO.getDescription());
		note.setTitle(updateDTO.getTitle());
		return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS, noteRepository.save(note));
	}

	/**
	 * Purpose:Method for deleting notes of a particular user
	 * @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id 
	 *
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response delete(String noteIdToken, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		int noteId = TokenUtility.tokenParserInt(noteIdToken);
		LOG.info(CommonFiles.SERVICE_DELETE_METHOD);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		
		if (note == null  || (!note.isTrash())) {
			throw new LabelException(CommonFiles.DELETE_NOTE_FAILED);
		}
		
		noteRepository.deleteById(noteId);
		return new Response(200, CommonFiles.DELETE_NOTE_SUCCESS, true);
	}

	/**
	 * Purpose: Method for getting all the note of a given user
	 * 
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response get(String emailIdToken) {
		LOG.info(CommonFiles.SERVICE_GET_METHOD);

		String emailId = TokenUtility.tokenParser(emailIdToken);

		List<Note> note = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId))
				.collect(Collectors.toList());

		if (note == null) {
			throw new NoteException(CommonFiles.GET_NOTE_FAILED);
		}

		return new Response(200, CommonFiles.GET_NOTE_SUCCESS, note);
	}

	/**
	 * Purpose: Method for pin and unpin note
	 * 
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response pin(String noteIdToken, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		int noteId = TokenUtility.tokenParserInt(noteIdToken);

		LOG.info(CommonFiles.SERVICE_PIN_METHOD);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isPin()) {
			
				note.setPin(false);
			} else {
				note.setArchive(false);
				note.setTrash(false);
				note.setPin(true);
			}

			return new Response(200, CommonFiles.PIN_SUCCESS, noteRepository.save(note));
		}
	}

	/**
	 * Purpose: Method for archive and unarchive a note
	 * 
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response archive(String noteIdToken, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		int noteId = TokenUtility.tokenParserInt(noteIdToken);
		LOG.info(CommonFiles.SERVICE_ARCHIVE_METHOD);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isArchive()) {
				note.setArchive(false);
			} else {
				note.setPin(false);
				note.setTrash(false);
				note.setArchive(true);
			}

			return new Response(200, CommonFiles.ARCHIVE_SUCCESS, noteRepository.save(note));

		}
	}
	
	
	/**
	 * Purpose: Method for  unarchive a note and Setting the Pin true
	 * 
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response archivePin(String noteIdToken, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		int noteId = TokenUtility.tokenParserInt(noteIdToken);
		LOG.info(CommonFiles.SERVICE_ARCHIVE_PIN_METHOD);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isArchive()) {
				note.setPin(true);
				note.setArchive(false);
			} 

			return new Response(200, CommonFiles.ARCHIVE_PIN_SUCCESS, noteRepository.save(note));

		}
	}


	/**
	 * Purpose:Method for trash and untrash a note
	 *  @param emailIdToken token containing email id 
	 * @param noteIdToken token containing note id 
	 *
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response trash(String noteIdToken, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		int noteId = TokenUtility.tokenParserInt(noteIdToken);
		LOG.info(CommonFiles.SERVICE_TRASH_METHOD);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		} else {
			if (note.isTrash()) {
				note.setTrash(false);
			} else {
				note.setPin(false);
				note.setTrash(true);
			}

			return new Response(200, CommonFiles.TRASH_SUCCESS, noteRepository.save(note));
		}

	}

	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * 
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response sortDate(String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		List<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId))
				.sorted((Note n1, Note n2) -> n1.getUpdate().compareTo(n2.getUpdate())).parallel()
				.collect(Collectors.toList());

		return new Response(200, CommonFiles.SORT_DATE_SUCCESS, sortedNote);

	}

	/**
	 * Purpose: Method for sorting notes of a user by name (title()
	 * 
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response sortName(String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		List<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId))
				.sorted((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle())).parallel()
				.collect(Collectors.toList());

		return new Response(200, CommonFiles.SORT_NAME_SUCCESS, sortedNote);
	}

}
