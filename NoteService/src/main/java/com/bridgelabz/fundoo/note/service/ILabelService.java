/******************************************************************************
 
 *  Purpose: This is interface of Label  class  it handles  all the 
 *           request coming from controller and  then process   in service 
 *           implementation class where all the method of this service is 
 *           implemented .
 *  		 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.LabelUpdateDTO;

import com.bridgelabz.fundoo.note.response.Response;

public interface ILabelService {
	
	/**
	 * Purpose: Method for adding label for a particular user
	 * @param labelDTO containing label details 
	 * @param token containing details of a user for which the
	 *        the label has to be made 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response add(LabelDTO labelDTO , String token);
	
	
	
	
	/**
	 *  Purpose: Method of updating labels of a particular
	 *           user 
	 * @param label containing the update data of a particular
	 *        label 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response update(LabelUpdateDTO labelUpdateDTO);
	
	
	
	
	/**
	 *  Purpose: Method for deleting labels of a particular user
	 * @param labelId of particular label which we want to delete
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response delete(int labelId);
	
	
	
	
	/**
	 *  Purpose: Method for fetching the labels 
	 * @param labelId of particular label which we want to get 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response get(int labelId);

}
