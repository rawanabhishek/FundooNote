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
import com.bridgelabz.fundoo.note.dto.UpdateDTO;
import com.bridgelabz.fundoo.note.response.Response;

public interface INoteService {
	
	/**
	 * @param noteDTO
	 * @return
	 */
	public Response addNote(NoteDTO noteDTO) ;
	
	/**
	 * @param userId
	 * @return
	 */
	public Response readNote(int userId);
	
	/**
	 * @param updateeDTO
	 * @return
	 */
	public Response updateNote(UpdateDTO updateeDTO) ;
	
	/**
	 * @param noteId
	 * @return
	 */
	public Response deleteNote(int noteId);
	
	
	/**
	 * @param id
	 * @return
	 */
	public Response pin(int id);
	
	/**
	 * @param id
	 * @return
	 */
	public Response archive(int id );
	
	/**
	 * @param id
	 * @return
	 */
	public Response trash(int id);

}
