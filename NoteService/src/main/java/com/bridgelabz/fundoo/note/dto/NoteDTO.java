/******************************************************************************
 
 *  Purpose: A class which is a  simple DTO(Data Transfer Object) which can map
 *  		 the values to this class on the basis of the user response which
 *  		 is catch by RequestBody annotation and then mapping into this use
 *           POJO class.
 *  		  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.dto;




public class NoteDTO {
	
	private String title;
	private String description;
	private boolean pin;
	private boolean archive;
	private int userId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPin() {
		return pin;
	}
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "NoteDTO [title=" + title + ", description=" + description + ", pin=" + pin + ", archive=" + archive
				+ ", userId=" + userId + "]";
	}
	
	
}
