/******************************************************************************
 
 *  Purpose: An interface class extending JPA repository  which give 
 *           its  service to use in the application .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   26-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Label;



@Repository
public interface  LabelRepository extends JpaRepository<Label, Integer>{

	/**
	 * Purpose: Method for finding label by labelId and emailId  of User
	 * @param id of particular label
	 * @param email of particular user
	 * @return
	 */
	Optional<Label> findByLabelIdAndEmailId(Integer id , String email);

	

	



	

}
