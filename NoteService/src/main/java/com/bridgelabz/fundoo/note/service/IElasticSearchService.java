/******************************************************************************
 
 *  Purpose: This is interface of Label  class  it handles  all the 
 *           request coming from controller and  then process   in service 
 *           implementation class where all the method of this service is 
 *           implemented .
 *  		 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   02-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import java.io.IOException;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.response.Response;

public interface IElasticSearchService {
	
	
	/**
	 * Purpose: Method for adding document to the elastic search database
	 * @param note object containing data of notes
	 * @return Response object containing status code , message 
	 *         and object .
	 * @throws Exception
	 */
	public Response addDocument(Note note) throws Exception;
	
	
	/**
	 * Purpose: Method for reading document from elastic search database
	 * @param id of the document
	 * @return
	 * @throws IOException
	 */
	public Response readDocument(String id) throws IOException;
	
	
	/**
	 * Purpose: Method for searching document from elastic database by the type of 
	 *          title or description
	 * @param value of the search 
	 * @param type by which the searching will be performed 
	 * @return Response object containing status code , message 
	 *         and object .
	 * @throws IOException
	 */
	public Response search(String value, String type) throws IOException ;
	
	
	/**
	 * Purpose: Method for deleting document from elastic database
	 * @param id of the document
	 * @return Response object containing status code , message 
	 *         and object .
	 * @throws IOException
	 */
	public Response deleteDocument(String id) throws IOException;
	
	/**
	 * Purpose: Method for searching document by title and description
	 * @param value
	 * @return Response object containing status code , message 
	 *         and object .
	 * @throws IOException
	 */
	public Response searchByTitleDescription(String value) throws IOException;

}
