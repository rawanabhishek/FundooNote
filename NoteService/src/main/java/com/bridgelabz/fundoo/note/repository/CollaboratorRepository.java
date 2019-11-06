/******************************************************************************
 
 *  Purpose: An interface class extending Jpa repository  which give 
 *           its  service to use in the application .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Collaborator;


@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer>{

	Collaborator findAllByEmail(String collaboratorEmail);

	Optional<Collaborator> findByCollaboratorIdAndEmail(Integer collaboratorId, String email);

	Optional<Collaborator> findByEmail(String Email);

}
