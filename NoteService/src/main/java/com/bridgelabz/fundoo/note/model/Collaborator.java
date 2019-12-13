package com.bridgelabz.fundoo.note.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
public class Collaborator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer collaboratorId;

	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String profilePic;
	
	
	
	
	

	@JsonIgnoreProperties(value = "collaborators")
	@ManyToMany(mappedBy = "collaborators", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;
	

}
