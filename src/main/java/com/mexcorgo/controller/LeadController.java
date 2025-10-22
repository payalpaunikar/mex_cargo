package com.mexcorgo.controller;


import com.mexcorgo.component.FollowUpStatus;
import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.*;
import com.mexcorgo.dto.response.GetLeadFullDetails;
import com.mexcorgo.dto.response.LeadResponseDto;
import com.mexcorgo.dto.response.LeadResponseInterface;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.LeadService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class LeadController {

    private LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }


    @PostMapping("/created/lead")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<Lead> createdNewLead(@RequestBody CreateLeadRequest createLeadRequest, Authentication authentication){
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
       return ResponseEntity.ok(leadService.createLead(createLeadRequest,currentUser));
    }


    @GetMapping("/get/currentuser/lead")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Page<LeadResponseDto> getUserLeads(
            @RequestParam Long userId,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size
    ){
         return leadService.getUserLeads(userId,page,size);
    }


      @GetMapping("/get/lead/{leadId}/company/details")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<Company> getCompanyDetailsForLead(@PathVariable("leadId")Long leadId){
        return ResponseEntity.ok(leadService.getCompanyDetailsForLead(leadId));
      }


      @GetMapping("/get/lead/{leadId}/company/emp/details")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<CompanyEmployee> getCompanyEmployeeDetailsForLead(@PathVariable("leadId")Long leadId){
         return ResponseEntity.ok(leadService.getCompanyEmployeeDetailsForLead(leadId));
      }


      @GetMapping("/get/lead/{leadId}/need")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<Need> getNeedForLead(@PathVariable("leadId")Long leadId){
         return ResponseEntity.ok(leadService.getNeedForLead(leadId));
      }


      @GetMapping("/get/lead/{leadId}/note")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<Note> getNoteForLead(@PathVariable("leadId")Long leadId){
         return ResponseEntity.ok(leadService.getNoteForLead(leadId));
      }


      @DeleteMapping("/delete/lead/{leadId}")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<Boolean> deleteTheLead(@PathVariable("leadId")Long leadId){
        return ResponseEntity.ok(leadService.deleteTheLead(leadId));
      }

      @GetMapping("/get/lead/{leadId}")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<LeadResponseDto> getLeadById(@PathVariable("leadId")Long leadId){
        return ResponseEntity.ok(leadService.getLeadById(leadId));
      }


      @PutMapping("/update/lead/{leadId}")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public ResponseEntity<LeadResponseDto> updateLeadDetails(@PathVariable("leadId")Long leadId, @RequestBody LeadRequestDto leadRequestDto){
        return ResponseEntity.ok(leadService.updateLeadDetails(leadId,leadRequestDto));
      }


      @PutMapping("/update/lead/{leadId}/company/details")
      @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
      public Company updateCompanyDetails(@PathVariable("leadId")Long leadId,@RequestBody CompanyRequest companyRequest){
        return leadService.updateCompanyDetails(leadId,companyRequest);
      }

    @PutMapping("/update/lead/{leadId}/company/emp/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public CompanyEmployee updateCompanyEmployeeDetails(@PathVariable("leadId")Long leadId,@RequestBody CompanyEmplyeeRequest companyEmplyeeRequest){
        return leadService.updateCompanyEmployeeDetails(leadId,companyEmplyeeRequest);
    }

    @PutMapping("/update/lead/{leadId}/need/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Need updateNeedDetails(@PathVariable("leadId")Long leadId, @RequestBody NeedRequest needRequest){
        return leadService.updateNeedDetails(leadId,needRequest);
    }

    @PutMapping("/update/lead/{leadId}/note/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Note updateNoteDetails(@PathVariable("leadId")Long leadId, @RequestBody NoteRequest noteRequest){
        return leadService.updateNote(leadId,noteRequest);
    }


    @PostMapping("/add/lead/{leadId}/followups")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<Note> addFollowUpToNote(
            @PathVariable Long leadId,
            @RequestBody FollowUp followUp,
            @RequestParam("followUpStatus")FollowUpStatus followUpStatus,
            Authentication authentication
            ) {
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
        Note updatedNote = leadService.addFollowUp(leadId, followUp,followUpStatus,currentUser);
        return ResponseEntity.ok(updatedNote);
    }

    @PostMapping("/add/lead/{leadId}/extra-need-data")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public NeedExtraDetails afterLeadConfirmAddExtraNeedData(@PathVariable("leadId")Long leadId,@RequestBody NeedExtraDetailsRequestDto needExtraDetailsRequestDto){
     return leadService.afterLeadConfirmAddExtraNeedData(leadId,needExtraDetailsRequestDto);
    }


    @GetMapping("/get/lead/{leadId}/extra-need-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public NeedExtraDetails getExtraNeedDetails(@PathVariable("leadId")Long leadId){
       return leadService.getExtraNeedDetails(leadId);
    }

    @PutMapping("/update/lead/{leadId}/extra-need-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public NeedExtraDetails updateExtraNeedDetails(@PathVariable("leadId")Long leadId,@RequestBody NeedExtraDetails needExtraDetails){
        return leadService.updateExtraNeedDetails(leadId,needExtraDetails);
    }

    @GetMapping("/lead/{leadId}/full-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public GetLeadFullDetails getLeadFullDetails(@PathVariable("leadId") Long leadId){
      return leadService.getFullLeadDetails(leadId);
    }

    @PutMapping("/update/lead/{leadId}/full-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public GetLeadFullDetails updateLeadFullDetails(@PathVariable("leadId")Long leadId,@RequestBody GetLeadFullDetails getLeadFullDetails){
     return leadService.updateFullLeadDetails(leadId,getLeadFullDetails);
    }

    @GetMapping("/lead/{leadId}/end-user-details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public EndUserDetails getEndUserByLeadId(@PathVariable("leadId")Long leadId){
        return leadService.getEndUserDetailsByLeadId(leadId);
    }


    @GetMapping("/get/all/lead/according")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<List<LeadResponseDto>> getLeadByFollowUpDate(@RequestParam String followUpDate){
        return ResponseEntity.ok(leadService.findLeadByFollowUpDate(LocalDate.parse(followUpDate)));
    }


    @GetMapping("/get/lead/{leadId}/particular-amount")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public QuatationParticularsAmount getQuatationParticularAmount(@PathVariable("leadId")Long leadId){
        return leadService.getParticularAmount(leadId);
    }


    @PutMapping("/update/lead/{leadId}/particular-amount")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public QuatationParticularsAmount updateQuatationParticularAmount(@PathVariable("leadId")Long leadId,
                                             @RequestBody AddParticularsRequestDto addParticularsRequestDto){
        return leadService.updateParticularAmount(leadId,addParticularsRequestDto);
    }



}
