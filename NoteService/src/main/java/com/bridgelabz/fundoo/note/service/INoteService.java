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
	 * @param noteDTO
	 * @return
	 */
	public Response add(NoteDTO noteDTO) ;
	
	/**
	 * @param userId
	 * @return
	 */
	public Response get(int userId);
	
	/**
	 * @param updateeDTO
	 * @return
	 */
	public Response update(NoteUpdateDTO updateDTO) ;
	
	/**
	 * @param noteId
	 * @return
	 */
	public Response delete(int noteId);
	
	
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
	
	
	public Response sortDate(int userId);
	
	public Response sortName(int userId);

}
