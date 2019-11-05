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
