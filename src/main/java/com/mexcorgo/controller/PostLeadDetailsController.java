package com.mexcorgo.controller;

import com.mexcorgo.datamodel.PostLeadDetails;
import com.mexcorgo.service.PostLeadDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostLeadDetailsController {

          private PostLeadDetailsService postLeadDetailsService;

    @Autowired
    public PostLeadDetailsController(PostLeadDetailsService postLeadDetailsService) {
        this.postLeadDetailsService = postLeadDetailsService;
    }

    @PostMapping("/lead/{leadId}/add/post-lead-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<PostLeadDetails> addPostLeadDetails(
            @PathVariable Long leadId,
            @RequestBody PostLeadDetails dto) {
        PostLeadDetails saved = postLeadDetailsService.addPostLeadDetails(leadId, dto);
        return ResponseEntity.ok(saved);
    }

}
