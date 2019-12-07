/******************************************************************************
 
 *  Purpose: This is a service class for userService implementing userService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.user.configuration.UserConfiguration;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;

import com.bridgelabz.fundoo.user.exception.custom.UserException;
import com.bridgelabz.fundoo.user.model.EmailData;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.utility.CommonFiles;
import com.bridgelabz.fundoo.user.utility.TokenUtility;


@Service
public class ImplUserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static final Logger LOG = LoggerFactory.getLogger(ImplUserService.class);


	@Override
	public Response userLogin(LoginDTO login) {
		LOG.info(CommonFiles.SERVICE_LOGIN_METHOD);

		if (!userRepository.findAll().stream()
				.anyMatch(i -> i.getEmail().equals(login.getEmail())
						&& userConfiguration.passwordEncoder().matches(login.getPassword(), i.getPassword())
						&& i.isVerified())) {
			throw new UserException(CommonFiles.LOGIN_FAILED);
		}
		String token = TokenUtility.tokenBuild(login.getEmail());
		return new Response(200, CommonFiles.LOGIN_SUCCESS, token);

	}


	@Override
	public Response userRegister(RegisterDTO register) {
		LOG.info(CommonFiles.SERVICE_REGISTER_METHOD);

		if (!userRepository.findByEmail(register.getEmail()).isEmpty()) {
			throw new UserException(register.getEmail() + CommonFiles.REGISTER_EMAIL_FOUND);

		}

		EmailData emailData = new EmailData();
		emailData.setEmail(register.getEmail());
		emailData.setToken(TokenUtility.tokenBuild(register.getEmail()));
		emailData.setMessage(CommonFiles.EMAIL_SUBJECT_VERIFY);
		emailData.setPath(CommonFiles.VERIFY_URL);

		rabbitTemplate.convertAndSend(CommonFiles.ROUTING_KEY, emailData);

		register.setPassword(userConfiguration.passwordEncoder().encode(register.getPassword()));
		User user = modelMapper.map(register, User.class);
		userRepository.save(user);

		return new Response(200, CommonFiles.REGISTER_SUCCESS, userRepository.save(user));

	}

	
	@Override
	public Response userForgotPassword(String email) {
		LOG.info(CommonFiles.SERVICE_FORGOTPASSWORD_METHOD);

		if (userRepository.findByEmail(email) == null) {
			throw new UserException(email + CommonFiles.EMAIL_FAILED);

		}
		EmailData emailData = new EmailData();
		emailData.setEmail(email);
		emailData.setToken(TokenUtility.tokenBuild(email));
		emailData.setMessage(CommonFiles.EMAIL_SUBJECT_SETPASSWORD);
		emailData.setPath(CommonFiles.SET_PASSWORD_URL);
		rabbitTemplate.convertAndSend(CommonFiles.ROUTING_KEY, emailData);

		return new Response(200, CommonFiles.EMAIL_SUCCESS, true);

	}


	@Override
	public Response userSetPassword(SetPasswordDTO setPasswordDTO, String token) {
		LOG.info(CommonFiles.SERVICE_SETPASSWORD_METHOD);

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(TokenUtility.tokenParser(token)))
				.findAny().orElse(null);

		if (user == null && !(setPasswordDTO.getPassword().equals(setPasswordDTO.getConfirmPassword()))) {

			throw new UserException(CommonFiles.SET_PASSWORD_FAILED);

		}
		user.setPassword(userConfiguration.passwordEncoder().encode(setPasswordDTO.getPassword()));

		return new Response(200, CommonFiles.SET_PASSWORD_SUCCESS, userRepository.save(user));

	}


	@Override
	public Response isVerified(String token) {
		LOG.info(CommonFiles.SERVICE_ISVERIFIED_METHOD);

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(TokenUtility.tokenParser(token)))
				.findAny().orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_NOT_VERIFIED);

		}
		user.setVerified(true);

		return new Response(200, CommonFiles.USER_VERIFIED, userRepository.save(user));

	}


	@Override
	public Response addProfilePic(String emailIdToken, MultipartFile file) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		Path path = Paths.get(CommonFiles.PROFILE_PIC_PATH + email + "." + extension);
		Files.write(path, bytes);

		user.setProfilePic(CommonFiles.PROFILE_PIC_PATH + email + "." + extension);

		return new Response(200, CommonFiles.PHOTO_ADDED_SUCCESS, userRepository.save(user));

	}


	@Override
	public Response removeProfilePic(String emailIdToken) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}
		Path path = Paths.get(user.getProfilePic());

		Files.delete(path);
		user.setProfilePic(null);
		return new Response(200, CommonFiles.PHOTO_REMOVED_SUCCESS, userRepository.save(user));
	}

	
	@Override
	public Response updateProfilePic(String emailIdToken, MultipartFile file) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}
//		if (user.getProfilePic() == null) {
//			throw new UserException(CommonFiles.NO_PROFILE_PIC);
//		}

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		Path path = Paths.get(CommonFiles.PROFILE_PIC_PATH + email + "." + extension);
		Files.write(path, bytes);

		user.setProfilePic(CommonFiles.PROFILE_PIC_PATH + email + "." + extension);

		return new Response(200, CommonFiles.PHOTO_UPDATED_SUCCESS, userRepository.save(user));
	}

	@Override
	public Response getProfilePic(String emailIdToken) {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}
		String profilePic = "";
		String filePath = CommonFiles.PROFILE_PIC_PATH;
		File fileFolder = new File(filePath);
		if (fileFolder != null) {
			for (final File file : fileFolder.listFiles()) {
				if (!file.isDirectory()) {
					String encodeBase64 = null;
					try {
						if ((CommonFiles.PROFILE_PIC_PATH + file.getName()).equals(user.getProfilePic())) {
							String extension = FilenameUtils.getExtension(file.getName());
							FileInputStream fileInputStream = new FileInputStream(file);
							byte[] bytes = new byte[(int) file.length()];
							fileInputStream.read(bytes);
							encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							profilePic = ("data:image/" + extension + ";base64," + encodeBase64);
							fileInputStream.close();
							break;
						}

					} catch (Exception e) {

					}
				}
			}
		}

		return new Response(200, CommonFiles.PATH_FEATCHED, profilePic);

	}

	
	@Override
	public Response getAllUser() {
		
		return new Response(200 , CommonFiles.GET_ALL_USER, userRepository.findAll().stream().collect(Collectors.toList()));
	}

}
