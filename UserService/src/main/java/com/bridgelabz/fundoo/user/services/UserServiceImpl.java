/******************************************************************************
 
 *  Purpose:  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   18-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public boolean userLogin(String email, String password) {
		return userRepository.findAll()
				.stream().anyMatch(i ->i.getEmail().equals(email) && i.getPassword().equals(password) );
	}

	@Override
	public boolean userRegister(User user) {
		
		if(!userEmailValidate(user.getEmail())) {
			userRepository.save(user);
		    return true;
		}else {
			return false;
		}
		
	
	}

	@Override
	public void userForgotPassword(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userSetPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userEmailValidate(String email) {
	   if( userRepository.findAll()
				.stream().anyMatch(i ->i.getEmail().equals(email) ))
		{
		   return false;
		}else {
			return true;
		}
	}


}
