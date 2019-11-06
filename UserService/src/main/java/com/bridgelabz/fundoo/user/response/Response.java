/******************************************************************************
 
 *  Purpose: To create a Response Class Will send HTTP(Hyper test transfer 
 *            protocol) , Exception message and  data Object.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	
	private int statusCode;
	
	private String message;
	
	private Object data;
	
	
}
