/******************************************************************************
 
 *  Purpose: An interface class extending JPA repository  which give 
 *           its  service to use in the application .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bridgelabz.fundoo.note.model.Note;


@Repository
public interface NoteRepository  extends JpaRepository<Note, Integer>{

	/**
	 * Purpose: Method for finding note by noteId and emailId
	 * @param id of particular note
	 * @param email of particular user
	 * @return
	 */
	Optional<Note> findByNoteIdAndEmailId(Integer id , String email);

	/**
	 * Purpose: Method for finding note by emailId
	 * @param emailId of particular user
	 * @return
	 */
	Optional<Note> findByEmailId(String emailId);
	

}
