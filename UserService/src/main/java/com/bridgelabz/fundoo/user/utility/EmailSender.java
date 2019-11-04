/******************************************************************************
 
 *  Purpose: Utility class which has the business login which can be
 *  		 used in the application whenever required.
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.utility;



import java.io.Serializable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.model.EmailData;

@Component
public class EmailSender  implements Serializable{
	
	
	
	@Autowired
	private JavaMailSender emailSender;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Purpose: To send email to user emailId for forgot password function.
	 * @param   To user emailId details.
	 * @param   Token for checking the user is authorized to change password 
	 *          or not.
	 * @param   url containing link for particular operations.         
	 * @param   Subject of the mail to be send.
	 * @return  SimpleMailMessage Object containing the mailing details of 
	 *          user and sender.
	 */
	
	public  void mailSender(EmailData emailData) {
		
		    
		         SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(emailData.getEmail()); 
		        message.setSubject(emailData.getMessage()); 
		        message.setText(emailData.getPath() + emailData.getToken());
		        emailSender.send(message);
		        
		        
		       
		    }
	
	
	

	
	


}
