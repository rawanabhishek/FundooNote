package com.bridgelabz.fundoo.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Collaborator;
import java.util.Optional;

public interface CollaboratorRepository  extends JpaRepository<Collaborator, Integer>{
	Collaborator findAllByEmail(String collaboratorEmail);

	Optional<Collaborator> findByCollaboratorIdAndEmail(Integer collaboratorId, String email);

	Optional<Collaborator> findByEmail(String Email);

}
