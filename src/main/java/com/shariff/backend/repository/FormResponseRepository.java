package com.shariff.backend.repository;

import com.shariff.backend.model.FormResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormResponseRepository extends JpaRepository<FormResponse, Integer> {
    List<FormResponse> findByFormIdAndResponderId(int formId, int responderId);
}
