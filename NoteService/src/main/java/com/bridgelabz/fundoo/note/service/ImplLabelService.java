/******************************************************************************
 
 *  Purpose: This is a service class for lavelService implementing labelService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
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
import com.bridgelabz.fundoo.note.utility.TokenUtility;

@Service
public class ImplLabelService implements ILabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private ApplicationConfiguration configuration;

	
	/**
	 * Purpose: Method for adding label for a particular user
	 * @param labelDTO containing label details 
	 * @param token containing details of a user for which the
	 *        the label has to be made 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response add(LabelDTO labelDTO , String token) {
		
		if(labelDTO==null) {
			throw new LabelException(CommonFiles.ADD_LABEL_FAILED);
		}
		String key =TokenUtility.tokenParser(token);
		
        
		Label label = configuration.modelMapper().map(labelDTO, Label.class);
		label.setUser(key);
		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, labelRepository.save(label));

	}

	
	
	
	/**
	 *  Purpose: Method of updating labels of a particular
	 *           user 
	 * @param label containing the update data of a particular
	 *        label 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response update(LabelUpdateDTO labelUpdateDTO) {
		Label label=labelRepository.findById(labelUpdateDTO.getLabelId()).orElse(null);
		if(label==null) {
			throw new LabelException(CommonFiles.UPDATE_LABEL_FAILED);
		}
		label.setName(labelUpdateDTO.getName());
		
		return new Response(200, CommonFiles.UPDATE_LABEL_SUCCESS, labelRepository.save(label));
	}

	
	
	

	/**
	 *  Purpose: Method for deleting labels of a particular user
	 * @param labelId of particular label which we want to delete
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response delete(int labelId) {
		if(labelId==0) {
			throw new LabelException(CommonFiles.DELETE_LABEL_FAILED);
		}
		labelRepository.deleteById(labelId);
		return new Response(200, CommonFiles.DELETE_LABEL_SUCCESS, true);

	}
	
	
	
	
	

	/**
	 *  Purpose: Method for fetching the labels 
	 * @param labelId of particular label which we want to get 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	@Override
	public Response get(int labelId) {
		if(labelId==0) {
			throw new LabelException(CommonFiles.GET_LABEL_FAILED);
		}
		return new Response(200, CommonFiles.GET_LABEL_SUCCESS,
				labelRepository.findAll().stream().filter(i -> i.getLabelId() == labelId));

	}

}
