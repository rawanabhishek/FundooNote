/******************************************************************************
 
 *  Purpose: This is a service class for lavelService implementing labelService
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
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.exception.custom.LabelException;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;

import com.bridgelabz.fundoo.note.utility.TokenUtility;

@Service
public class ImplLabelService implements ILabelService {

	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private NoteRepository  noteRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);


	@Override
	public Response add(LabelDTO labelDTO, String emailIdToken) {
		LOG.info(CommonFiles.SERVICE_ADD_METHOD);
		
		String key = TokenUtility.tokenParser(emailIdToken);

		if (labelDTO == null) {
			throw new LabelException(CommonFiles.ADD_LABEL_FAILED);
		}
		
		if(labelRepository.findAll().stream().anyMatch(i -> i.getName().
				equals(labelDTO.getName()) && i.getEmailId().equals(key))) {
			throw new LabelException(CommonFiles.LABEL_ALREADY_PRESENT);
		}
		

		

		Label label = configuration.modelMapper().map(labelDTO, Label.class);
		label.setEmailId(key);

		labelRepository.save(label);

		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, label);

	}

	
	@Override
	public Response update(LabelDTO labelDTO, int labelId) {

		LOG.info(CommonFiles.SERVICE_UPDATE_METHOD);
         if(labelDTO.getName()==null) {
        	 throw new LabelException(CommonFiles.UPDATE_LABEL_FAILED);
         }
		Label label = labelRepository.findById(labelId).orElse(null);
		if (label == null) {
			throw new LabelException(CommonFiles.UPDATE_LABEL_FAILED);
		}
		label.setName(labelDTO.getName());

		return new Response(200, CommonFiles.UPDATE_LABEL_SUCCESS, labelRepository.save(label));
	}

	
	@Override
	public Response delete(int labelId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_DELETE_METHOD);

		Label label = labelRepository.findByLabelIdAndEmailId(labelId, emailId).orElse(null);
		if (label == null) {

			throw new LabelException(CommonFiles.DELETE_LABEL_FAILED);
 
		}
		
		List<Note> notes=noteRepository.findAll().stream().filter(i -> i.getLabels().
				stream().anyMatch(j -> j.getLabelId() == labelId)).collect(Collectors.toList());
	
		for(Note note : notes) {
			note.getLabels().remove(label);
			noteRepository.save(note);
		}
		
		labelRepository.deleteById(labelId);
		return new Response(200, CommonFiles.DELETE_LABEL_SUCCESS, true);

	}


	@Override
	public Response get( String emailIdToken) {

		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_GET_METHOD);
		
		return new Response(200, CommonFiles.GET_LABEL_SUCCESS,
				labelRepository.findAll().stream().filter(i -> i.getEmailId().equals(emailId)));

	}

}
