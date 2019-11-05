/******************************************************************************
 
 *  Purpose: This is a service class for CollaboratorService implementing Collaborator
 *           Service interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.exception.custom.CollaboratorException;

import com.bridgelabz.fundoo.note.model.Collaborator;

import com.bridgelabz.fundoo.note.repository.CollaboratorRepository;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;
import com.bridgelabz.fundoo.note.utility.TokenUtility;

@Service
public class ImplCollaboratorService  implements ICollaboratorService{
	
	@Autowired
	CollaboratorRepository collaboratorRepository;

	@Override
	public Response add(String emailIdToken) {
		String email = TokenUtility.tokenParser(emailIdToken);
		Collaborator collaborator=collaboratorRepository.findByEmail(email).orElse(null);
		if(collaborator!=null) {
			throw new CollaboratorException(CommonFiles.COLLABORATOR_PRESENT_ALREADY);
		}
		
		Collaborator collaboratorAdd=new Collaborator();
		collaboratorAdd.setEmail(email);
		
		
		return new Response(200, CommonFiles.COLLABORATOR_ADDED_SUCCESS,collaboratorRepository.save(collaboratorAdd) );
	}

	@Override
	public Response delete(String emailIdToken, int collaboratorId) {
		String email = TokenUtility.tokenParser(emailIdToken);
		Collaborator collaborator=collaboratorRepository.findByCollaboratorIdAndEmail(collaboratorId, email).orElse(null);
		
		if(collaborator == null) {
			 throw new CollaboratorException(CommonFiles.COLLABORATOR_FOUND_FAILED);
		}
		collaboratorRepository.deleteById(collaboratorId);
		
		return new Response(200, CommonFiles.COLLABORATOR_ADDED_SUCCESS,collaboratorRepository.save(collaborator) );
	}


	@Override
	public Response get(String emailIdToken,int collaboratorId) {
		String email = TokenUtility.tokenParser(emailIdToken);
		Collaborator collaborator=collaboratorRepository.findByCollaboratorIdAndEmail(collaboratorId, email).orElse(null);
		if(collaborator == null) {
			 throw new CollaboratorException(CommonFiles.COLLABORATOR_FOUND_FAILED);
		}
		
		return new Response(200, CommonFiles.COLLABORATOR_FOUND_SUCCESS,collaborator);
	}

}
