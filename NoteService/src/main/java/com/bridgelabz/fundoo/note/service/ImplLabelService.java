/******************************************************************************
 
 *  Purpose: This is a service class for lavelService implementing labelService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.configuration.ApplicationConfiguration;

import com.bridgelabz.fundoo.note.dto.LabelDTO;

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

	public static final Logger LOG = LoggerFactory.getLogger(ImplNoteService.class);

	/**
	 * Purpose: Method for adding label for a particular user
	 * 
	 * @param labelDTO containing label details
	 * @param token    containing details of a user for which the the label has to
	 *                 be made
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response add(LabelDTO labelDTO, String emailIdToken) {
		LOG.info(CommonFiles.SERVICE_ADD_METHOD);

		if (labelDTO == null) {
			throw new LabelException(CommonFiles.ADD_LABEL_FAILED);
		}

		String key = TokenUtility.tokenParser(emailIdToken);

		Label label = configuration.modelMapper().map(labelDTO, Label.class);
		label.setEmailId(key);

		labelRepository.save(label);

		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, label);

	}

	/**
	 * Purpose: Method of updating labels of a particular user
	 * 
	 * @param labelIdToken containing labbelId details
	 * @param labelDTO     containing the update data of a particular label
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response update(LabelDTO labelDTO, int labelId) {

		LOG.info(CommonFiles.SERVICE_UPDATE_METHOD);

		Label label = labelRepository.findById(labelId).orElse(null);
		if (label == null) {
			throw new LabelException(CommonFiles.UPDATE_LABEL_FAILED);
		}
		label.setName(labelDTO.getName());

		return new Response(200, CommonFiles.UPDATE_LABEL_SUCCESS, labelRepository.save(label));
	}

	/**
	 * Purpose: Method for deleting labels of a particular user
	 * 
	 * @param labelIdToken containing labbelId details
	 * @param emailIdToken containing emailId details
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response delete(int labelId, String emailIdToken) {
		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_DELETE_METHOD);

		Label label = labelRepository.findByLabelIdAndEmailId(labelId, emailId).orElse(null);
		if (label == null) {

			throw new LabelException(CommonFiles.DELETE_LABEL_FAILED);

		}

		labelRepository.deleteById(labelId);
		return new Response(200, CommonFiles.DELETE_LABEL_SUCCESS, true);

	}

	/**
	 * Purpose: Method for fetching the labels
	 * 
	 * @param labelIdToken containing labbelId details
	 * @param emailIdToken containing emailId details
	 * @return Response object containing status code , message and object .
	 */
	@Override
	public Response get(int labelId, String emailIdToken) {

		String emailId = TokenUtility.tokenParser(emailIdToken);

		LOG.info(CommonFiles.SERVICE_GET_METHOD);
		if (labelId == 0) {
			throw new LabelException(CommonFiles.GET_LABEL_FAILED);
		}
		return new Response(200, CommonFiles.GET_LABEL_SUCCESS,
				labelRepository.findById(labelId).filter(i -> i.getEmailId().equals(emailId)));

	}

}
