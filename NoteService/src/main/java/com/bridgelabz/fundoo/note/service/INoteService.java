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




import java.util.Date;



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
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response get(String emailIdToken);
	
	/**
	 * Purpose: Method for updating notes of a particular user 
	 * @param updateDTO containing the updated data for a particular note
	 *        and setting its value to model and saving it to the
	 *        database.
	 * @param noteIdToken token containing note id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response update(NoteUpdateDTO updateDTO , int noteId ,String emailIdToken) ;
	
	/**
	 * Purpose:Method for deleting notes of a particular user 
	 * @param emailIdToken token containing email id 
	 * @param noteId containing note id 
	 *        
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response delete(int noteId , String emailIdToken);
	
	
	/**
	 * Purpose: Method for pin and unpin note
	 * @param emailIdToken token containing email id 
	 * @param noteId containing note id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response pin(int noteId, String emailIdToken);
	
	
	
	/**
	 * Purpose: Method for archive and unarchive a note
	 * @param emailIdToken token containing email id 
	 * @param noteId containing note id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response archive(int noteId , String emailIdToken );
	
	/**
	 * Purpose: Method for  unarchive a note and Setting the Pin true
	 * @param emailIdToken token containing email id 
	 * @param noteId  containing note id
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response archivePin(int noteId , String emailIdToken );
	
	/**
	 * Purpose:Method for trash and untrash a note
	 * @param emailIdToken token containing email id 
	 * @param noteId containing note id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response trash(int noteId, String emailIdToken);
	
	
	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response sortDate(String emailIdToken);
	
	/**
	 * Purpose: Method for sorting notes of a user by name (title()  
	 * @param emailIdToken token containing email id 
	 * @return Response object containing status code , message 
	 *         and object .
	 */
	public Response sortName(String emailIdToken);
	
	
	
	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * @param noteId containing note id 
	 * @param emailIdToken  containing email id
	 * @param labelId containing label id 
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response addLabel(int noteId, String emailIdToken, int labelId );
	

	
	/**
	 * Purpose: Method for sorting notes of a user by updated date
	 * @param noteId containing note id 
	 * @param emailIdToken containing email id
	 * @param labelId containing label id 
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response removeLabel(int noteId , String emailIdToken , int labelId);
	
	
	public Response addCollaborator(int noteId , String emailIdToken,String collaboratorEmail);
	
	
	public Response removeCollaborator(int noteId , String emailIdToken, String collaboratorEmail);
	
	/**
	 * Purpose: Method for adding reminder to a  note
	 * @param noteId
	 * @param emailIdToken containing email id
	 * @param date for adding reminder
	 * @return
	 */
	public Response addReminder(int noteId ,String emailIdToken, Date date );
	
	/**
	 * Purpose: Method for updating the reminder of a note
	 * @param noteId containing note id 
	 * @param emailIdToken
	 * @param date for updating reminder
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response updateReminder(int noteId, String emailIdToken , Date date);
	
	/**
	 * Purpose: Method for removing reminder of a note
	 * @param noteId containing note id 
	 * @param emailIdToken containing email id
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response removeReminder(int noteId ,String emailIdToken);
	
	/**
	 * Purpose: Method for adding color to a note 
	 * @param noteId containing note id 
	 * @param emailIdToken containing email id
	 * @param color code for a note in hex color format
	 * @return
	 */
	public Response addColor(int noteId , String emailIdToken , String color);
	
	
	/**
	 * Purpose: Method for updating color of a  note
	 * @param noteId containing note id 
	 * @param emailIdToken containing email id
	 * @param color code for a note in hex color format
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response updateColor(int noteId, String emailIdToken, String color);
	
	/**
	 * Purpose: Method for removing color of a  note
	 * @param noteId containing note id 
	 * @param emailIdToken containing email id
	 * @return Response object containing status code , message 
	 *         and object 
	 */
	public Response removeColor(int noteId , String emailIdToken);
	

	

}
