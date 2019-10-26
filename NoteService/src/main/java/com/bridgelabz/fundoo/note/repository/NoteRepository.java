/******************************************************************************
 
 *  Purpose: An interface class extending Jpa repository interface which can
 *           give service to use the implementation of JpaRepository .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;


@Repository
public interface NoteRepository  extends JpaRepository<Note, Integer>{

	

}
