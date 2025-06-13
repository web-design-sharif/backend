package com.shariff.backend.repository;

import com.shariff.backend.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {

    // Get all forms where this user is the owner
    List<Form> findByOwner_Id(int ownerId);

    // Get all forms where this user is a submitter
    @Query("SELECT f FROM Form f JOIN f.submitters s WHERE s.id = :userId")
    List<Form> findBySubmittersId(@Param("userId") int userId);
}
