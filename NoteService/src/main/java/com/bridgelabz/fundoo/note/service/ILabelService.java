package com.bridgelabz.fundoo.note.service;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.LabelUpdateDTO;

import com.bridgelabz.fundoo.note.response.Response;

public interface ILabelService {
	
	/**
	 * Purpose:
	 * @param labelDTO
	 * @return
	 */
	public Response add(LabelDTO labelDTO);
	
	/**
	 *  Purpose:
	 * @param label
	 * @return
	 */
	public Response update(LabelUpdateDTO labelUpdateDTO);
	
	/**
	 *  Purpose:
	 * @param labelId
	 * @return
	 */
	public Response delete(int labelId);
	
	/**
	 *  Purpose:
	 * @param labelId
	 * @return
	 */
	public Response get(int labelId);

}
