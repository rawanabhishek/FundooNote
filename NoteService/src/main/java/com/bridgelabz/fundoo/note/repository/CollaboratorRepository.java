/******************************************************************************
 
 *  Purpose: An interface class extending JPA repository  which give 
 *           its  service to use in the application .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Collaborator;
import java.util.Optional;

public interface CollaboratorRepository  extends JpaRepository<Collaborator, Integer>{
	Collaborator findAllByEmail(String collaboratorEmail);

	/**
	 * Purpose: Method for finding collaborator by collaboratorId and email
	 * @param collaboratorId
	 * @param email 
	 * @return
	 */
	Optional<Collaborator> findByCollaboratorIdAndEmail(Integer collaboratorId, String email);

	/**
	 * Purpose: Method for finding collaborator by email
	 * @param Email id of collaborator
	 * @return
	 */
	Optional<Collaborator> findByEmail(String Email);

}
