package com.bridgelabz.fundoo.note.model;

import java.util.Date;



import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class User {
	
    private int id;

	

    private String firstName;


    private String lastName;

	
    private String password;

	
    private String email;

	
    private String contact;

	
	private Date dateCreate;

	
	private Date dateUpdate;


	private boolean isVerified;
	

}
