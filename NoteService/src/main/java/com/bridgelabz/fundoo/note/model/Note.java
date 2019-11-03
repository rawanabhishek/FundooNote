/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of UserService users and this 
 *            this class uses @Entity annotation to get know spring that it is 
 *            an entity.
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Note {

	@Id
	@Column(name = "note_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer noteId;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "is_pin", columnDefinition = "boolean default false")
	private boolean pin;

	@Column(name = "is_archive", columnDefinition = "boolean default false")
	private boolean archive;

	@Column(name = "is_trash", columnDefinition = "boolean default false")
	private boolean trash;

	@CreationTimestamp
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@UpdateTimestamp
	@Column(name = "date_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date update;

	@Column(name = "reminder")
	private Date reminder;


	
    @Pattern(regexp="^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|([0]{0})$")
    private String noteColor;

	@JsonIgnoreProperties(value = "notes")
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Label> labels;
	
	

//	@OneToMany
//	private List<String> collaborator=new ArrayList<String>();

}
