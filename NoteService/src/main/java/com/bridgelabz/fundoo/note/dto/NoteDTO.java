/******************************************************************************
 
 *  Purpose: A class which is a  simple DTO(Data Transfer Object) which is use to
 *           map its field to the POJO class on the basis of the user response which
 *  		 is catch by Request 
 *  		  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import com.bridgelabz.fundoo.note.model.Label;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoteDTO extends JdkSerializationRedisSerializer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	private String description;

	private boolean pin;
	private Date remainder;
	private boolean archive;
	private List<Label> labels;
	private String noteColor;

}
