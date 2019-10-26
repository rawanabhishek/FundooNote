/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of UserService users and this 
 *            this class uses Entity to get know spring that it is POJO class.
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.Getter;
import lombok.Setter;




@Entity
@Table(name = "note")
@Getter
@Setter
public class Note {
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer noteId;
	
	
	@Column(name="user_id")
	private int userId;
	
	
    @Column(name="title")
	private String title;
	
    @Column(name="description")
	private String description;
	
    @Column(name="is_pin" , columnDefinition = "boolean default false")
	private boolean isPin;
	
    @Column(name="is_archive" , columnDefinition = "boolean default false")
	private boolean isArchive;
	
    @Column(name="is_trash" , columnDefinition = "boolean default false")
	private boolean isTrash;
	
    @CreationTimestamp
    @Column(name="date_created")
    @Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
 

	@UpdateTimestamp
    @Column(name="date_updated")
    @Temporal(TemporalType.TIMESTAMP)
	private Date update;
	
    @Column(name="remainder")
	private Date remainder;

	

	
	
}
