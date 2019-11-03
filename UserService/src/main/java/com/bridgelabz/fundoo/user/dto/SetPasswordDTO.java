/******************************************************************************
 
 *  Purpose: A class which is a  simple DTO(Data Transfer Object) which can map
 *  		 the values to this class on the basis of the user response which
 *  		 is catch by RequestBody annotation and then mapping into this use
 *           POJO class. 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SetPasswordDTO {
	
	@NotNull(message="please provide password")
	private String password;
	
	
	@NotNull(message="please provide confirm password")
	private String confirmPassword;
	

	
	
	

}
