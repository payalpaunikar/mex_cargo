package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    Note findByLead_LeadId(Long leadId);
}
