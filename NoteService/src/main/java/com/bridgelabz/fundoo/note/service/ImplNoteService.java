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



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.UpdateDTO;
import com.bridgelabz.fundoo.note.exception.custom.AddNoteException;
import com.bridgelabz.fundoo.note.exception.custom.UpdateNoteException;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;


@Service
public class ImplNoteService implements INoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	

	
	@Autowired
	private ModelMapper modelMapper;
	
	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);

	@Override
	public Response addNote(NoteDTO noteDTO)  {
		System.out.println("note dto--->"+noteDTO);
		LOG.info("inside impl add note");
		
		if(!(noteDTO.getTitle().isEmpty() || noteDTO.getDescription().isEmpty())) {
			Note note=modelMapper.map(noteDTO, Note.class);
			//note.setUserId(2);
			System.out.println("before save"+note);
			LOG.info("inside mapping add note");
			Note newNote=noteRepository.save(note);
			return new Response(200, CommonFiles.ADD_NOTE_SUCCESS, newNote );
		}else {
			LOG.info("inside impl add note failed");
			throw new AddNoteException("creation of new note failed");
		}
	
		
	}



	@Override
	public Response updateNote(UpdateDTO updateDTO)  {
		
		if(!(updateDTO.getTitle().isEmpty() || updateDTO.getDescription().isEmpty())) {
			Note note=modelMapper.map(updateDTO, Note.class);
			LOG.info("inside mapping update note");
			
			return new Response(200, CommonFiles.UPDATE_NOTE_SUCCESS,noteRepository.save(note)) ;
		}else {
			LOG.info("inside impl update note failed");
			throw new UpdateNoteException("Updation of note failed");
		}
	}

	@Override
	public Response deleteNote(int noteId) {
		
		noteRepository.deleteById(noteId);
		return new Response(200,CommonFiles.DELETE_NOTE_SUCCESS ,  true);
	}

	@Override
	public Response readNote(int userId) {
		
	 
		return new Response(200, CommonFiles.GET_NOTE_SUCCESS,noteRepository.findAll().stream().filter(i -> i.getUserId()==userId));
	}



	@Override
	public Response pin(int id) {
		Note note=noteRepository.findById(id).orElse(null);
		if(note.isPin()) {
			note.setPin(false);
		}else {
			note.setPin(true);
		}
		
		return new Response(200,CommonFiles.PIN_SUCCESS,noteRepository.save(note) );
		
	}



	@Override
	public Response archive(int id) {
		Note note=noteRepository.findById(id).orElse(null);
		if(note.isArchive()) {
			note.setArchive(false);
		}else {
			note.setArchive(true);
		}
		
		return new Response(200,CommonFiles.ARCHIVE_SUCCESS,noteRepository.save(note));
		
	}



	@Override
	public Response trash(int id) {
		Note note=noteRepository.findById(id).orElse(null);
		if(note.isTrash()) {
			note.setTrash(false);
		}else {
			note.setTrash(true);
		}
		
		return new Response(200,CommonFiles.TRASH_SUCCESS,noteRepository.save(note));
		
	}

}
