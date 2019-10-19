/******************************************************************************
 
 *  Purpose:  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   18-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.utility;



import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {
	
	
	public  SimpleMailMessage sendSimpleMessage(
		      String to, String token) {
		     
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(to); 
		        message.setSubject("Reset your password or UsersService"); 
		        message.setText("http://localhost:8080/user/setpassword/" + token);
		        return message;
		        
		       
		    }
	
	


}
