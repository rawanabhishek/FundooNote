/******************************************************************************
 
 *  Purpose: This is a service class for NoteService implementing noteService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoo.note.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.NoteUpdateDTO;
import com.bridgelabz.fundoo.note.exception.custom.NoteException;
import com.bridgelabz.fundoo.note.model.Collaborator;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.model.User;
import com.bridgelabz.fundoo.note.repository.CollaboratorRepository;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;

import com.bridgelabz.fundoo.note.utility.TokenUtility;

@Service
public class ImplNoteService implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private ImplElasticSearchService elasticService;

	@Autowired
	private RestTemplate restTemplate;

	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);

	@Override
	public Response add(NoteDTO noteDTO, String token) {

		LOG.info(CommonFiles.SERVICE_ADD_METHOD);

		if ((noteDTO.getTitle().isBlank() && noteDTO.getDescription().isBlank())) {

			throw new NoteException(CommonFiles.ADD_NOTE_FAILED);

		}

		Note note = configuration.modelMapper().map(noteDTO, Note.class);
		System.out.println(note);
		if (note.getNoteColor() == null || note.getNoteColor().isBlank()) {
			note.setNoteColor(CommonFiles.COLOR_DEFAULT_VALUE);
		}
		note.setEmailId(TokenUtility.tokenParser(token));
		noteRepository.save(note);

		try {
			elasticService.addDocument(note);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new Response(200, CommonFiles.ADD_NOTE_SUCCESS, note);

	}

	@Override
	public Response update(NoteUpdateDTO updateDTO, int noteId, String emailIdToken) {
		LOG.info(CommonFiles.SERVICE_UPDATE_METHOD);
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (((updateDTO.getTitle().isBlank() && updateDTO.getDescription().isBlank())) && note == null) {
			throw new NoteException(CommonFiles.UPDATE_NOTE_FAILED);
		}

		note.setDescription(updateDTO.getDescription());
		note.setTitle(updateDTO.getTitle());

		try {
			elasticService.addDocument(note);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS, noteRepository.save(note));
	}

	@Override
	public Response delete(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_DELETE_METHOD);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);

		if (note == null) {
			throw new NoteException(CommonFiles.DELETE_NOTE_FAILED);
		}
		if (!note.isTrash()) {
			throw new NoteException(CommonFiles.NOTE_TRASH);
		}
		try {
			elasticService.deleteDocument(note.getNoteId().toString());
		} catch (IOException e) {

			e.printStackTrace();
		}
		noteRepository.deleteById(noteId);
		return new Response(200, CommonFiles.DELETE_NOTE_SUCCESS, true);
	}

	@Override
	public Response get(String emailIdToken, boolean pin, boolean archive, boolean trash) {
		LOG.info(CommonFiles.SERVICE_GET_METHOD);
		LOG.info("pin=" + pin + "archice=" + archive + "trash=" + trash);
		String emailId = TokenUtility.tokenParser(emailIdToken);

		List<Note> note = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId)
				&& i.isPin() == pin && i.isArchive() == archive && i.isTrash() == trash).collect(Collectors.toList());

		if (note == null) {
			throw new NoteException(CommonFiles.GET_NOTE_FAILED);
		}

		return new Response(200, CommonFiles.GET_NOTE_SUCCESS, note);
	}

	@Override
	public Response pin(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

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

	@Override
	public Response archive(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

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

	@Override
	public Response archivePin(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

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

	@Override
	public Response trash(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

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

	@Override
	public Response sortDate(String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		List<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId))
				.sorted((Note n1, Note n2) -> n1.getUpdate().compareTo(n2.getUpdate())).parallel()
				.collect(Collectors.toList());

		return new Response(200, CommonFiles.SORT_DATE_SUCCESS, sortedNote);

	}

	@Override
	public Response sortName(String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_SORT_METHOD);

		List<Note> sortedNote = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId))
				.sorted((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle())).parallel()
				.collect(Collectors.toList());

		return new Response(200, CommonFiles.SORT_NAME_SUCCESS, sortedNote);
	}

	@Override
	public Response addLabel(int noteId, String emailIdToken, int labelId) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}

		Label label = labelRepository.findById(labelId).orElse(null);

		if (label == null) {

			throw new NoteException(CommonFiles.LABEL_FOUND_FAILED);
		}
		if (note.getLabels().contains(label)) {
			throw new NoteException(CommonFiles.LABEL_PRESENT);
		}

		note.getLabels().add(label);

		noteRepository.save(note);

		return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS, note);
	}

	@Override
	public Response removeLabel(int noteId, String emailIdToken, int labelId) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);

		Label label = labelRepository.findById(labelId).orElse(null);

		if (note.getLabels().contains(label) && label != null) {

			note.getLabels().remove(label);
			noteRepository.save(note);
		}
		return new Response(200, CommonFiles.REMOVE_LABEL_SUCCESS, note);
	}

	@Override
	public Response addCollaborator(int noteId, String emailIdToken, String collaboratorEmail) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		String collaboratorEmailId = TokenUtility.tokenParser(collaboratorEmail);

		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getUserList();
		System.out.println("note id " + noteId + " emailId" + emailId + "Collb email" + collaboratorEmailId);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);

		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}


		Collaborator collaborator = new Collaborator();
		System.out.println("Note is not null");
		
		 User user= users.stream().findAny().filter(i ->
		 i.getEmail().equals(collaboratorEmailId)).orElse(null);

		if (user == null) {
			System.out.println("User is null");
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		}

		
		collaborator.setEmail(collaboratorEmailId);
		collaboratorRepository.save(collaborator);
	
		note.getCollaborators().add(collaborator);

		return new Response(200, CommonFiles.ADD_COLLABORATOR_SUCCESS, noteRepository.save(note));
	}

	@Override
	public Response removeCollaborator(int noteId, String emailIdToken, String collaboratorEmail) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}

		Collaborator collaborator = collaboratorRepository.findAllByEmail(collaboratorEmail);

		if (collaborator == null) {
			throw new NoteException(CommonFiles.USER_FOUND_FAILED);
		}
		note.getCollaborators().remove(collaborator);
		return new Response(200, CommonFiles.REMOVE_COLLABORATOR_SUCCESS, noteRepository.save(note));
	}

	@Override
	public Response addReminder(int noteId, String emailIdToken, Date date) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);

		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
		if (note.getReminder() != null) {
			throw new NoteException(CommonFiles.REMINDER_PRESENT);
		}
		if (date.before(new Date())) {
			throw new NoteException(CommonFiles.INVALID_DATE);
		}
		note.setReminder(date);
		return new Response(200, CommonFiles.ADD_REMAINDER_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response updateReminder(int noteId, String emailIdToken, Date date) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
		if (date.before(new Date())) {
			throw new NoteException(CommonFiles.INVALID_DATE);
		}
//		if (note.getReminder() != null) {
//			throw new NoteException(CommonFiles.NO_REMINDER);
//		}
		note.setReminder(date);
		return new Response(200, CommonFiles.UPDATE_REMAINDER_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response removeReminder(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
		note.setReminder(null);
		return new Response(200, CommonFiles.REMOVE_REMAINDER_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response addColor(int noteId, String emailIdToken, String color) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
		note.setNoteColor(color);

		return new Response(200, CommonFiles.COLOR_ADDED_SUCCESS, noteRepository.save(note));
	}

	@Override
	public Response removeColor(int noteId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
		if (note.getNoteColor() == null) {
			throw new NoteException(CommonFiles.COLOR_ABSENT);
		}
		note.setNoteColor(null);
		return new Response(200, CommonFiles.COLOR_REMOVED_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response updateColor(int noteId, String emailIdToken, String color) {
	
		String emailId = TokenUtility.tokenParser(emailIdToken);
		Note note = noteRepository.findByNoteIdAndEmailId(noteId, emailId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}
//		if (note.getNoteColor().equals(CommonFiles.COLOR_DEFAULT_VALUE)) {
//			throw new NoteException(CommonFiles.DEFAULT_COLOR_PRESENT);
//		}
		note.setNoteColor(color);

		return new Response(200, CommonFiles.COLOR_UPDATED_SUCCESS, noteRepository.save(note));
	}

	@Override
	public Response searchByTitleDescription(String searchString, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		Note note = noteRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId)).findAny()
				.orElse(null);

		if (note == null) {
			System.out.println("Failed to fetch ");
			throw new NoteException(CommonFiles.NOTE_FOUND_FAILED);
		}

		try {

			return elasticService.searchByTitleDescription(searchString);
		} catch (IOException e) {
			throw new NoteException(e.toString());
		}

	}

	@Override
	public Response getUsers() {

		return new Response(200, CommonFiles.COLLABORATOR_FOUND_SUCCESS, getUserList());
	}

	private Object getUserList() {
		Response response = restTemplate.getForObject("http://user-service/user/alluser", Response.class);
		
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) response.getData();
		return users;
	

	}

}
