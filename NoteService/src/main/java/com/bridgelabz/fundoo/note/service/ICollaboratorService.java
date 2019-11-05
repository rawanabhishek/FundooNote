/******************************************************************************
 
 *  Purpose: This is interface of Collaborator  class  it handles  all the 
 *           request coming from controller and  then process   in service 
 *           implementation class where all the method of this service is 
 *           implemented .
 *  		 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;



import com.bridgelabz.fundoo.note.response.Response;

public interface ICollaboratorService {
	
	/**
	 * Purpose: Method for adding the collaborator 
	 * @param emailIdToken containing collaborator email id
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response add(String emailIdToken );
	
	
	/**
	 * Purpose: Method for deleting the collaborator and its data
	 * @param emailIdToken containing collaborator email id
	 * @param collaboratorId  of a particular collaborator
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response delete(String emailIdToken , int collaboratorId );
	
	
	/**
	 * Purpose: Method for fetching the collaborator data
	 * @param emailIdToken containing collaborator email id
	 * @param collaboratorId of a particular collaborator
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response get(String emailIdToken,int collaboratorId);
	

}
