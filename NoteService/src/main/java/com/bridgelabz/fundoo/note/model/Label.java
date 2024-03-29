/******************************************************************************
 
 *  Purpose:  A Class  for creating the POJO class Of UserService users and this 
 *            this class uses @Entity annotation to get know spring that it is 
 *            an entity.
 *  		 
 *  @author   Abhishek Rawat
 *  @version  1.0
 *  @since    26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "label_id")
	private Integer labelId;

	
	@Column(name="email_id")
	private String emailId;

	@Column(name="name")
	private String name;

	@CreationTimestamp
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@UpdateTimestamp
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	
	
	@ManyToMany(mappedBy = "labels", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Note> notes;

}
