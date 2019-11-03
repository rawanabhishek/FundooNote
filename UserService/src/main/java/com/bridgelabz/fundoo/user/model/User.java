/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of UserService users and this 
 *            this class uses Entity to get know spring that it is POJO class.
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.Data;


@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;

	
	@Column(name = "first_name")
    private String firstName;

	@Column(name = "last_name")
    private String lastName;

	@Column(name = "password")
    private String password;

	@Column(name = "email")
    private String email;

	@Column(name = "contact")
    private String contact;

	@CreationTimestamp
	@Column(name = "date_create")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreate;

	@UpdateTimestamp
	@Column(name = "date_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Column(name = "is_verified", columnDefinition = "boolean default false")
	private boolean isVerified;
	
	@Lob
	private String profilePic;

	

}
