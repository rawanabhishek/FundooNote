/******************************************************************************
 
 *  Purpose: This is interface of Note Service class  it handles  all the 
 *           request coming from controller and  then process   in service 
 *           implementation class where all the method of this service is 
 *           implemented .
 *  		 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
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

@Service
public class ImplNoteService implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);

	@Override
	public Response add(NoteDTO noteDTO) {

		if ((noteDTO.getTitle().isEmpty() || noteDTO.getDescription().isEmpty())) {

			throw new NoteException(CommonFiles.ADD_NOTE_FAILED);

		}

		Note note = configuration.modelMapper().map(noteDTO, Note.class);

		return new Response(200, CommonFiles.ADD_NOTE_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response update(NoteUpdateDTO updateDTO) {

		if (!(updateDTO.getTitle().isEmpty() || updateDTO.getDescription().isEmpty())) {

			Note note = noteRepository.findById(updateDTO.getNoteId()).orElse(null);
			note.setDescription(updateDTO.getDescription());
			note.setTitle(updateDTO.getTitle());
			return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS, noteRepository.save(note));
		} else {

			throw new NoteException(CommonFiles.UPDATE_NOTE_FAILED);
		}
	}

	@Override
	public Response delete(int noteId) {
		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null) {
			throw new LabelException(CommonFiles.DELETE_NOTE_FAILED);
		}
		noteRepository.deleteById(noteId);
		return new Response(200, CommonFiles.DELETE_NOTE_SUCCESS, true);
	}

	@Override
	public Response get(int userId) {
		Note note = noteRepository.findById(userId).orElse(null);
		if (note == null) {
			throw new NoteException(CommonFiles.READ_NOTE_FAILED);
		}

		return new Response(200, CommonFiles.GET_NOTE_SUCCESS,
				noteRepository.findAll().stream().filter(i -> i.getUserId() == userId));
	}

	@Override
	public Response pin(int id) {
		Note note = noteRepository.findById(id).orElse(null);
		if (note.isPin()) {
			note.setPin(false);
		} else {
			note.setPin(true);
		}

		return new Response(200, CommonFiles.PIN_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response archive(int id) {
		Note note = noteRepository.findById(id).orElse(null);
		if (note.isArchive()) {
			note.setArchive(false);
		} else {
			note.setArchive(true);
		}

		return new Response(200, CommonFiles.ARCHIVE_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response trash(int id) {
		Note note = noteRepository.findById(id).orElse(null);
		if (note.isTrash()) {
			note.setTrash(false);
		} else {
			note.setTrash(true);
		}

		return new Response(200, CommonFiles.TRASH_SUCCESS, noteRepository.save(note));

	}

	@Override
	public Response sortDate(int userId) {

		Stream<Note> sortedNote =  noteRepository.findAll().stream().filter(i -> i.getUserId()==userId).
				sorted((Note n1, Note n2) -> n1.getUpdate().compareTo(n2.getUpdate())).parallel();

		return new Response(200, CommonFiles.SORT_DATE_SUCCESS, sortedNote);

	}

	@Override
	public Response sortName(int userId) {

		Stream<Note> sortedNote =  noteRepository.findAll().stream().filter(i -> i.getUserId()==userId).
				sorted((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle())).parallel();
		

		return new Response(200, CommonFiles.SORT_NAME_SUCCESS, sortedNote);
	}

}
