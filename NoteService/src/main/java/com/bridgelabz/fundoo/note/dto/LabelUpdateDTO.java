/******************************************************************************
 
 *  Purpose: A class which is a  simple DTO(Data Transfer Object) which can map
 *  		 the values to this class on the basis of the user response which
 *  		 is catch by RequestBody annotation and then mapping into this use
 *           POJO class.
 *  		  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelUpdateDTO {
	
	private String name;
	
	private Integer labelId;

}
