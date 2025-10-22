package com.mexcorgo.service;


import com.mexcorgo.component.FollowUpStatus;
import com.mexcorgo.datamodel.Lead;
import com.mexcorgo.datamodel.PostLeadDetails;
import com.mexcorgo.exception.InvalidLeadStatusException;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.LeadRepository;
import com.mexcorgo.repository.PostLeadDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLeadDetailsService {

      private PostLeadDetailsRepository postLeadDetailsRepository;

      private LeadRepository leadRepository;


    @Autowired
    public PostLeadDetailsService(PostLeadDetailsRepository postLeadDetailsRepository, LeadRepository leadRepository) {
        this.postLeadDetailsRepository = postLeadDetailsRepository;
        this.leadRepository = leadRepository;
    }


    public PostLeadDetails addPostLeadDetails(Long leadId, PostLeadDetails dto){

        Lead existingLead = leadRepository.findById(leadId)
                .orElseThrow(()-> new ResourceNotFoundException("Lead not found with id: " + leadId));

        //check lead status
        FollowUpStatus leadStatus = existingLead.getNote().getFollowUpStatus();

        // ❗ Check lead status
        if (leadStatus != FollowUpStatus.COMPLETED) {
            throw new InvalidLeadStatusException("Cannot add post lead details. Lead status must be COMPLETED.");
        }

        // Link and save post details
        dto.setLead(existingLead);
        PostLeadDetails savedDetails = postLeadDetailsRepository.save(dto);

        // Optional: set it in lead as well (bidirectional)
        existingLead.setPostLeadDetails(savedDetails);
        leadRepository.save(existingLead);

        return savedDetails;

    }

}
