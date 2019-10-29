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



import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.dto.NoteUpdateDTO;

import com.bridgelabz.fundoo.note.response.Response;

public interface INoteService {
	

	/**
	 * Purpose: Method for adding new note for user 
	 * @param noteDTO containing note data which will  be later map to the note 
	 *        model .
	 * @param token containing user details for which the particular note will
	 *        be created . To read the data of the token we are parsing the 
	 *        token and getting the user data.
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response add(NoteDTO noteDTO , String token) ;
	
	/**
	 * Purpose: Method for getting all the note of a given user
	 * @param userId for which we have to fetch all the note
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response get(String user);
	
	/**
	 * Purpose: Method for updating notes of a particular user 
	 * @param updateDTO containing the updated data for a particular note
	 *        and setting its value to model and saving it to the
	 *        database.
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response update(NoteUpdateDTO updateDTO) ;
	
	/**
	 * Purpose:Method for deleting notes of a particular user 
	 * @param noteId of a note which we need to delete it from the
	 *        database
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response delete(int noteId);
	
	
	/**
	 * Purpose: Method for pin and unpin note
	 * @param id of note which we need to pin or unpin 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response pin(int id);
	
	/**
	 * Purpose: Method for archive and unarchive a note
	 * @param id of note which we need to archive or unarchive
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response archive(int id );
	
	/**
	 * Purpose:Method for trash and untrash a note
	 * @param id of note which we need to trash or untrash
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response trash(int id);
	
	
	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * @param user for which we have to sort all the notes of that user
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response sortDate(String user);
	
	/**
	 * Purpose: Method for sorting notes of a user by name (title()  
	 * @param user for which we have to sort all the notes of that user
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response sortName(String user);

}
