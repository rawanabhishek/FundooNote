/******************************************************************************
 
 *  Purpose: Utility class which has the business login which can be
 *  		 used in the application whenever required.
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.rabbitmq;



import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {
	
	
	
	
	
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
	
	
	
	public static  SimpleMailMessage mailSender(
		      String to) {
		     
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(to); 
		        message.setSubject("Hie"); 
		        message.setText("how are you");
		        return message;
		        
		       
		    }
	
	
	
	
	


}