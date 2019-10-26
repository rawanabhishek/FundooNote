package com.bridgelabz.fundoo.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.configuration.ApplicationConfiguration;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.LabelUpdateDTO;
import com.bridgelabz.fundoo.note.exception.custom.LabelException;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;

@Service
public class ImplLabelService implements ILabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	@Override
	public Response add(LabelDTO labelDTO) {
		
		if(labelDTO==null) {
			throw new LabelException(CommonFiles.ADD_LABEL_FAILED);
		}

		Label label = configuration.modelMapper().map(labelDTO, Label.class);
		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, labelRepository.save(label));

	}

	@Override
	public Response update(LabelUpdateDTO labelUpdateDTO) {
		Label label=labelRepository.findById(labelUpdateDTO.getLabelId()).orElse(null);
		if(label==null) {
			throw new LabelException(CommonFiles.UPDATE_LABEL_FAILED);
		}
		label.setName(labelUpdateDTO.getName());
		
		return new Response(200, CommonFiles.UPDATE_LABEL_SUCCESS, labelRepository.save(label));
	}

	@Override
	public Response delete(int labelId) {
		if(labelId==0) {
			throw new LabelException(CommonFiles.DELETE_LABEL_FAILED);
		}
		labelRepository.deleteById(labelId);
		return new Response(200, CommonFiles.DELETE_LABEL_SUCCESS, true);

	}

	@Override
	public Response get(int labelId) {
		if(labelId==0) {
			throw new LabelException(CommonFiles.GET_LABEL_FAILED);
		}
		return new Response(200, CommonFiles.GET_LABEL_SUCCESS,
				labelRepository.findAll().stream().filter(i -> i.getLabelId() == labelId));

	}

}
