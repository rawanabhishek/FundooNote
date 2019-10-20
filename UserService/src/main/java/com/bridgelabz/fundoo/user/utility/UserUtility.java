/******************************************************************************
 
 *  Purpose: Utility class which has the business login which can be
 *  		 used in the application whenever required.
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.utility;



import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {
	
	
	
	
	
	/**
	 * Purpose: to send email to user emailId for forgot password function.
	 * @param   to user emailId details.
	 * @param   token for checking the user is authorized to change password 
	 *          or not.
	 * @return  SimpleMailMessage Object containing the mailing details of 
	 *          user and sender.
	 */
	public  SimpleMailMessage forgotMail(
		      String to, String token) {
		     
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(to); 
		        message.setSubject("Reset your password for UsersService"); 
		        message.setText("http://localhost:8080/user/setpassword/" + token);
		        return message;
		        
		       
		    }
	
	
	/**
	 * Purpose: to send email to user emailId for verification of emailId 
	 *          function.
	 * @param   to user emailId details.
	 * @param   token for checking the user is authorized to change password 
	 *          or not.
	 * @return  SimpleMailMessage Object containing the mailing details of 
	 *          user and sender.
	 */
	public  SimpleMailMessage verificationMail(
		      String to, String token) {
		     
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(to); 
		        message.setSubject("Verify your mail for UserService"); 
		        message.setText("http://localhost:8080/user/verify/" + token);
		        return message;
		        
		       
		    }
	
	


}
