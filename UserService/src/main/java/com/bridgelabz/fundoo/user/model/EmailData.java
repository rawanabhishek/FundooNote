/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of EmailData 
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailData  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String message;
	
	private String token;
	
	private String path;

}
