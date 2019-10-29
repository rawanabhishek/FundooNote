/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of UserService users and this 
 *            this class uses Entity to get know spring that it is POJO class.
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.model;

import java.util.Date;


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
@Setter
@Getter
@Table(name = "label")
public class Label {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer labelId;
	
	private String user;
	
	private String name;
	
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
 

	@UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

}