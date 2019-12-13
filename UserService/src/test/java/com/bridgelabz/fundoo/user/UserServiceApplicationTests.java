package com.bridgelabz.fundoo.user;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.bridgelabz.fundoo.user.configuration.UserConfiguration;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.exception.custom.UserException;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.service.ImplUserService;
import com.bridgelabz.fundoo.user.utility.CommonFiles;


@SpringBootTest
class UserServiceApplicationTests {

	
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserConfiguration userConfiguration;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private ImplUserService  userService;
	
	User user = new User();
	@Test
	public void registerUserTest()  throws Exception{
		RegisterDTO registerDto = new RegisterDTO();
		registerDto.setEmail("pradeepmotors93@gmail.com");
		registerDto.setContact("9821791313");
		registerDto.setFirstName("Abhishek");
		registerDto.setLastName("Rawat");
		registerDto.setPassword("rawan");
		
		
		//when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(registerDto.getEmail()).isEmpty()).
		thenThrow(new UserException(registerDto.getEmail() + CommonFiles.REGISTER_EMAIL_FOUND));
		//when(userConfiguration.passwordEncoder().encode(registerDto.getPassword())).thenReturn("password encoded");
		when(modelMapper.map(registerDto, User.class)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		Response response = userService.userRegister(registerDto);
		assertEquals(200, response.getStatusCode());
		
		
		
	
		
	}
	
	
	
	
	
	
	
	

}
