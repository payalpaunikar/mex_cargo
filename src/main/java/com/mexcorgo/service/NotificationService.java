package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.QuatationMasterPricingResponseSqlDto;
import com.mexcorgo.dto.request.AddParticularsRequestDto;
import com.mexcorgo.dto.request.SendEmailToAccountExecutiveRequest;
import com.mexcorgo.dto.response.ClientDetailsWhichWeTransferToAccountExcutiveDto;
import com.mexcorgo.dto.response.EmailReceiverInfo;
import com.mexcorgo.dto.response.NeedEmailDetails;
import com.mexcorgo.dto.response.QutationSelectedMasterForServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

        private SendEmailService sendEmailService;

    @Autowired
    public NotificationService(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @Async
    public void sendQuotationAssignmentEmailsToPurchaseExcutive(Set<User> executives, Quatation quotation, NeedEmailDetails need) {
        for (User executive : executives) {
            try {
                String subject = "Action Required: New Quotation Assigned - " + quotation.getLead().getLeadReferenceNo();
                String message = buildMessageForPurchaseExecutive(quotation, need, executive.getUserName());
                sendEmailService.sendEmail(executive.getEmail(), subject, message);
            } catch (Exception e) {
                System.err.println("Failed to send email to " + executive.getEmail());
                e.printStackTrace(); // or use logger
            }
        }
    }


    private String buildMessageForPurchaseExecutive(Quatation q, NeedEmailDetails n, String userName) {
        return "<html>" +
                "<body>" +
                "<p>Dear " + userName + ",</p>" +
                "<p>You have been assigned a new quotation request. Please review the details below:</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + q.getLead().getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + n.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + n.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + n.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + n.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + n.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + n.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + n.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + n.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + n.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + n.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + n.getCarMovingTime() + "</td></tr>" +
                "<tr><th>Quotation Ref No</th><td>" + q.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><th>Quotation Required Date</th><td>" + q.getQuatationRequiredDate() + "</td></tr>" +
                "<tr><th>Quotation Required Time</th><td>" + q.getQuatationRequiredTime() + "</td></tr>" +
                "</table>" +

                "<p>Please coordinate with the master to collect pricing details as soon as possible.</p>" +
                "<p>Best regards,<br/>" +
                "Mex Cargo Sales Team<br/>" +
                "<a href='mailto:sales@mexcargo.com'>sales@mexcargo.com</a><br/>" +
                "Contact: +91-XXXXXXXXXX" +
                "</p>" +
                "</body>" +
                "</html>";
    }


    @Async
    public void sendEndUserDetailsWithNeedWithQuatationDetailsToPlanningExecutive(Set<User> planningExcutives,Lead lead,Quatation quatation,
                                                               List<QuatationMasterPricingResponseSqlDto>dtos,Need n,
                                                               EndUserDetails endUserDetails){
        for (User executive : planningExcutives){
            try {
                String subject = "New Lead Assigned for Planning & Pricing Analysis - Lead Ref: " + lead.getLeadReferenceNo();
                String message = buildMessageForPlanningExecutive(quatation,lead,dtos,n,endUserDetails,executive.getUserName());
                sendEmailService.sendEmail(executive.getEmail(), subject, message);
            } catch (Exception e) {
                System.err.println("Failed to send email to " + executive.getEmail());
                e.printStackTrace(); // or use logger
            }
        }
    }


    private String buildMessageForPlanningExecutive(Quatation q,Lead lead,List<QuatationMasterPricingResponseSqlDto> allEntries,
                                                    Need n,EndUserDetails endUserDetails,String planningExecutiveName) {
        String masterResponses = "";  // Initialize masterResponses before building content

        // Build the content for master responses
        for (QuatationMasterPricingResponseSqlDto dto : allEntries) {
            masterResponses += "<table style=\"border-collapse: collapse; width: 100%; max-width: 600px;\" border=\"1\" cellpadding=\"8\">" +
                    "<tr><th align=\"left\">Associate Code</th><td>" + dto.getAssociateCode() + "</td></tr>" +
                    "<tr><th align=\"left\">Company Name</th><td>" + dto.getCompanyName() + "</td></tr>" +
                    "<tr><th align=\"left\">Contact Person Name</th><td>" + dto.getContactName() +"</td></tr>" +
                    "<tr><th align=\"left\">Contact Phone Number</th><td>" + dto.getContactNumber() + "</td></tr>" +
                    "<tr><th align=\"left\">Email</th><td>" + dto.getEmailId() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Sector</th><td>" + dto.getServiceSector() + "</td></tr>" +
                    "<tr><th align=\"left\">Location</th><td>" + dto.getLocation() + "</td></tr>" +
                    "<tr><th align=\"left\">Hub</th><td>" + dto.getHub() + "</td></tr>" +
                    "<tr><th align=\"left\">State</th><td>" + dto.getState() + "</td></tr>" +
                    "<tr><th align=\"left\">Grade</th><td>" + dto.getGrade() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Package Cost</th><td>₹" + dto.getOriginalPackageCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Package Cost</th><td>₹" + dto.getFinalPackageCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Trs Cost</th><td>" + dto.getOriginalTrsCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Trs Cost</th><td>" + dto.getFinalTrsCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Car Service Cost</th><td>" + dto.getOriginalCarServiceCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Car Service Cost</th><td>" + dto.getFinalCarServiceCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Additional Service Cost</th><td>" + dto.getOriginalAdditionalCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Additional Service Cost</th><td>" + dto.getFinalAdditionalCost() + "</td></tr>" +
                    "</table><br>";
        }

        return "<html>" +
                "<body>" +
                "<p>Dear " + planningExecutiveName + ",</p>" +
                "<p>A new lead has been assigned to you for planning and pricing analysis. Please review the following details:</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + lead.getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Client Name</th><td>" + endUserDetails.getUserName() + "</td></tr>" +
                "<tr><th>Client Department</th><td>" + endUserDetails.getDepartment() + "</td></tr>" +
                "<tr><th>Client Designation</th><td>" + endUserDetails.getDesignation() + "</td></tr>" +
                "<tr><th>Client Contact No</th><td>" + endUserDetails.getContactNo() + "</td></tr>" +
                "<tr><th>Client Landline No</th><td>" + endUserDetails.getLandLineNo() + "</td></tr>" +
                "<tr><th>Client MailId</th><td>" + endUserDetails.getMailId() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + n.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + n.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + n.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + n.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + n.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + n.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + n.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + n.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + n.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + n.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + n.getCarMovingTime() + "</td></tr>" +
                "<tr><th>Quotation Ref No</th><td>" + q.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><th>Quotation Required Date</th><td>" + q.getQuatationRequiredDate() + "</td></tr>" +
                "<tr><th>Quotation Required Time</th><td>" + q.getQuatationRequiredTime() + "</td></tr>" +
                "</table>" +

                "<p><strong>Master Responses:</strong></p>" +
                masterResponses +

//                "<p><strong>Analyze Pricing:</strong></p>" +
//                "<table border='1' cellpadding='5' cellspacing='0'>" +
//                "<tr><th>Analyze Package Cost</th><td>" + q.getAnalyzePackageCost() + "</td></tr>" +
//                "<tr><th>Analyze Trs Cost</th><td>" + q.getAnalyzeTrsCost() + "</td></tr>" +
//                "<tr><th>Analyze Car Service Cost</th><td>" + q.getAnalyzeCarServiceCost() + "</td></tr>" +
//                "<tr><th>Analyze Additional Service Cost</th><td>" + q.getAnalyzeAdditionalServiceCost() + "</td></tr>" +
//                "</table>" +

                "<p><b>Responsibilities:</b></p>" +
                "<ul>" +
                "<li>Coordinate with assigned masters to receive pricing based on the required services.</li>" +
                "<li>Analyze and finalize the best pricing option based on service quality and rates.</li>" +
                "<li>Select a master and forward the finalized details to the Project Executive for execution.</li>" +
                "</ul>" +

                "<p>Make sure to complete the analysis within the quotation deadline.</p>" +

                "<p>Best regards,<br/>" +
                "Mex Cargo Sales Team<br/>" +
                "<a href='mailto:sales@mexcargo.com'>sales@mexcargo.com</a><br/>" +
                "Contact: +91-XXXXXXXXXX" +
                "</p>" +
                "</body>" +
                "</html>";
    }

    @Async
    public void sendClientDetailsWithParticularAmountToAccountExecutive(ClientDetailsWhichWeTransferToAccountExcutiveDto clientDetails,QuatationParticularsAmount quatationParticularsAmount,User accountExecutive){
            try {
                String subject = "Quotation Amount Received - Lead Ref: "+clientDetails.getLeadReferenceNo();
                String message = buildEmailContentForAccountsExecutiveToReceivedParticularAmount(clientDetails,quatationParticularsAmount,accountExecutive);
                sendEmailService.sendEmail(accountExecutive.getEmail(), subject, message);
            } catch (Exception e) {
                System.err.println("Failed to send email to " + accountExecutive.getEmail());
                e.printStackTrace(); // or use logger
            }

    }


    private String buildEmailContentForAccountsExecutiveToReceivedParticularAmount(ClientDetailsWhichWeTransferToAccountExcutiveDto clientDetails,QuatationParticularsAmount quatationParticularsAmount,User accountExceutive) {
        return "<html>" +
                "<body>" +
                "<p>Dear " + accountExceutive.getUserName() + ",</p>" +

                "<p>A quotation amount has been assigned for the following lead. Please review the details and proceed as per standard procedure:</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + clientDetails.getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Client Name</th><td>" + clientDetails.getUserName() + "</td></tr>" +
                "<tr><th>Client Contact Number</th><td>" +clientDetails.getContactNo()  + "</td></tr>" +
                "<tr><th>Client Email Id</th><td>" + clientDetails.getMailId() + "</td></tr>" +
                "<tr><th>Packaging Amount</th><td>" + quatationParticularsAmount.getPackingAmount() + "</td></tr>" +
                "<tr><th>Loading Amount</th><td>" + quatationParticularsAmount.getLoadingAmount() + "</td></tr>" +
                "<tr><th>Unloading Amount</th><td>₹" + quatationParticularsAmount.getUnloadingAmount() + "</td></tr>" +
                "<tr><th>Unpacking Amount</th><td>₹" + quatationParticularsAmount.getUnpackingAmount()+ "</td></tr>" +
                "<tr><th>Packing And Loading Amount</th><td>₹" + quatationParticularsAmount.getPackingAndLoadingAmount() + "</td></tr>" +
                "<tr><th>Unloading And Unpacking Amount</th><td>₹" + quatationParticularsAmount.getUnloadingAndUnpackingAmount() + "</td></tr>" +
                "<tr><th>Packing,Loading,Unloading And Unpacking Amount</th><td>₹" + quatationParticularsAmount.getPackingAndLoadingAndUnloadingAndUnpackingAmount() + "</td></tr>" +
                "<tr><th>Transportation OF Household Amount</th><td>₹" + quatationParticularsAmount.getTransportationOfHouseholdAmount() + "</td></tr>" +
                "</table>" +

                "<p>Kindly ensure this amount is recorded and processed accordingly.</p>" +

                "<p>Best regards,<br/>" +
                "Mex Cargo - Sales Department<br/>" +
                "<a href='mailto:sales@mexcargo.com'>sales@mexcargo.com</a><br/>" +
                "Contact: +91-XXXXXXXXXX" +
                "</p>" +
                "</body>" +
                "</html>";
    }



    @Async
    public void sendEmailQuatationToMasters(List<String> recipients,Lead lead,NeedEmailDetails needEmailDetails){
        for (String email : recipients) {
            try {
                String subject = "Quotation Request for Your Services – Ref No: " + lead.getLeadReferenceNo();
                String message = buildEmailContentForMaster(lead,needEmailDetails);
                sendEmailService.sendEmail(email, subject, message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String buildEmailContentForMaster(Lead lead, NeedEmailDetails needEmailDetails) {
        return "<html>" +
                "<body>" +
                "<p>Dear Service Provider,</p>" +
                "<p>You have received a new quotation request. Kindly review the details below and provide your best pricing at your earliest convenience:</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + lead.getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + needEmailDetails.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + needEmailDetails.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + needEmailDetails.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + needEmailDetails.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + needEmailDetails.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + needEmailDetails.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + needEmailDetails.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + needEmailDetails.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + needEmailDetails.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + needEmailDetails.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + needEmailDetails.getCarMovingTime() + "</td></tr>" +
                "</table>" +

                "<p>Please respond with your quotation details by replying to this email or by contacting the purchase executive.</p>" +
                "<p>Best regards,<br/>" +
                "Mex Cargo - Purchase Department<br/>" +
                "<a href='mailto:purchase@mexcargo.com'>purchase@mexcargo.com</a><br/>" +
                "Contact: +91-XXXXXXXXXX" +
                "</p>" +
                "</body>" +
                "</html>";
    }





    @Async
    public void sendEmailQuatationWithMasterPricingToPricingExcutives(List<String> recipients,QuatationMasterPricingResponseSqlDto firstEntryDto,
                                                                      List<QuatationMasterPricingResponseSqlDto> quatationMasterPricingResponseSqlDtoList){
        for (String email : recipients) {
            try {
                String subject = "New Quotation for Pricing Review – Ref No: " + firstEntryDto.getLeadReferenceNo();
                String message = buildPricingQuotationEmailContent(firstEntryDto,quatationMasterPricingResponseSqlDtoList);
                sendEmailService.sendEmail(email, subject, message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String buildPricingQuotationEmailContent(QuatationMasterPricingResponseSqlDto firstEntry,
                                                     List<QuatationMasterPricingResponseSqlDto> allEntries) {
        String masterResponses = "";  // Initialize masterResponses before building content

        // Build the content for master responses
        for (QuatationMasterPricingResponseSqlDto dto : allEntries) {
            masterResponses += "<table style=\"border-collapse: collapse; width: 100%; max-width: 600px;\" border=\"1\" cellpadding=\"8\">" +
                    "<tr><th align=\"left\">Associate Code</th><td>" + dto.getAssociateCode() + "</td></tr>" +
                    "<tr><th align=\"left\">Company Name</th><td>" + dto.getCompanyName() + "</td></tr>" +
                    "<tr><th align=\"left\">Contact Person Name</th><td>" + dto.getContactName() +"</td></tr>" +
                    "<tr><th align=\"left\">Contact Phone Number</th><td>" + dto.getContactNumber() + "</td></tr>" +
                    "<tr><th align=\"left\">Email</th><td>" + dto.getEmailId() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Sector</th><td>" + dto.getServiceSector() + "</td></tr>" +
                    "<tr><th align=\"left\">Location</th><td>" + dto.getLocation() + "</td></tr>" +
                    "<tr><th align=\"left\">Hub</th><td>" + dto.getHub() + "</td></tr>" +
                    "<tr><th align=\"left\">State</th><td>" + dto.getState() + "</td></tr>" +
                    "<tr><th align=\"left\">Grade</th><td>" + dto.getGrade() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Package Cost</th><td>₹" + dto.getOriginalPackageCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Package Cost</th><td>₹" + dto.getFinalPackageCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Trs Cost</th><td>" + dto.getOriginalTrsCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Trs Cost</th><td>" + dto.getFinalTrsCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Car Service Cost</th><td>" + dto.getOriginalCarServiceCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Car Service Cost</th><td>" + dto.getFinalCarServiceCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Original Additional Service Cost</th><td>" + dto.getOriginalAdditionalCost() + "</td></tr>" +
                    "<tr><th align=\"left\">Final Additional Service Cost</th><td>" + dto.getFinalAdditionalCost() + "</td></tr>" +
                    "</table><br>";
        }

        // Now return the complete HTML content
        return "<html>" +
                "<body style=\"font-family: Arial, sans-serif; line-height: 1.6;\">" +
                "<p>Dear Pricing Executive,</p>" +
                "<p>You have received a new quotation for pricing review.</p>" +

                "<p><strong>Quotation Details:</strong></p>" +
                "<table style=\"border-collapse: collapse; width: 100%; max-width: 600px;\" border=\"1\" cellpadding=\"8\">" +
                "<tr><th align=\"left\">Field</th><th align=\"left\">Details</th></tr>" +
                "<tr><td><strong>Lead Reference No</strong></td><td>" + firstEntry.getLeadReferenceNo() + "</td></tr>" +
                "<tr><td><strong>Origin</strong></td><td>" + firstEntry.getSource() + "</td></tr>" +
                "<tr><td><strong>Destination</strong></td><td>" + firstEntry.getDestination() + "</td></tr>" +
                "<tr><td><strong>Commodity</strong></td><td>" + firstEntry.getCommodity() + "</td></tr>" +
                "<tr><td><strong>Size</strong></td><td>" + firstEntry.getSize() + "</td></tr>" +
                "<tr><td><strong>Weight</strong></td><td>" + firstEntry.getWeight() + "</td></tr>" +
                "<tr><td><strong>Transportation Type</strong></td><td>" + firstEntry.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><td><strong>Moving Date And Time</strong></td><td>" + firstEntry.getMovingDateAndTime() + "</td></tr>" +
                "<tr><td><strong>Other Service</strong></td><td>" + firstEntry.getOtherServices() + "</td></tr>" +
                "<tr><td><strong>Car Name</strong></td><td>" + firstEntry.getCarTransport() + "</td></tr>" +
                "<tr><td><strong>Car Moving Date</strong></td><td>" + firstEntry.getCarMovingDate() + "</td></tr>" +
                "<tr><td><strong>Car Moving Time</strong></td><td>" + firstEntry.getCarMovingTime() + "</td></tr>" +
                "<tr><td><strong>Quotation Reference No</strong></td><td>" + firstEntry.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><td><strong>Qutation Required Date</strong></td><td>" + firstEntry.getQuatationRequiredDate() + "</td></tr>" +
                "<tr><td><strong>Quatation Required Time</strong></td><td>" + firstEntry.getQuatationRequiredTime() + "</td></tr>" +
                "</table><br>" +

                "<p><strong>Master Responses:</strong></p>" +
                masterResponses +

                "<p>Please review the above details and proceed with the pricing decision.</p>" +
                "<p><strong>Best Regards,</strong><br> Mex Cargo</p>" +
                "</body>" +
                "</html>";
    }


    @Async
    public void sendEmailQuatationAfterPricingToSalesExecutives(Lead lead,Quatation quatation,NeedEmailDetails needEmailDetails,String username,String userEmail){
        try {
            String subject = "Quotation Reviewed – Ref No: " + lead.getLeadReferenceNo();
            String message = buildMessageForQuatationCreatorAfterAnalyzePrice(quatation,needEmailDetails,username);
            sendEmailService.sendEmail(userEmail, subject, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send pricing analysis email to: " + userEmail, e);
        }
    }


    private String buildMessageForQuatationCreatorAfterAnalyzePrice(Quatation q, NeedEmailDetails n, String userName) {
        return "<html>" +
                "<body>" +
                "<p>Dear " + userName + ",</p>" +
                "<p>The pricing analysis for the below quotation has been completed and is now being forwarded to you for further action.</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + q.getLead().getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + n.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + n.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + n.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + n.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + n.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + n.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + n.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + n.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + n.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + n.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + n.getCarMovingTime() + "</td></tr>" +
                "<tr><th>Quotation Ref No</th><td>" + q.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><th>Quotation Required Date</th><td>" + q.getQuatationRequiredDate() + "</td></tr>" +
                "<tr><th>Quotation Required Time</th><td>" + q.getQuatationRequiredTime() + "</td></tr>" +
                "<tr><th>Analyze Package Cost</th><td>" + q.getAnalyzePackageCost() + "</td></tr>" +
                "<tr><th>Analyze Trs Cost</th><td>" + q.getAnalyzeTrsCost() + "</td></tr>" +
                "<tr><th>Analyze Car Service Cost</th><td>" + q.getAnalyzeCarServiceCost() + "</td></tr>" +
                "<tr><th>Analyze Additional Service Cost</th><td>" + q.getAnalyzeAdditionalServiceCost() + "</td></tr>" +
                "</table>" +

                "<p>Please proceed with the necessary follow-up with the client and update the system accordingly.</p>" +
                "<p>For any clarifications, you may contact the pricing department.</p>" +

                "<p>Best regards,<br/>" +
                "Mex Cargo Pricing Team<br/>" +
                "<a href='mailto:pricing@mexcargo.com'>pricing@mexcargo.com</a><br/>" +
                "Contact: +91-XXXXXXXXXX" +
                "</p>" +
                "</body>" +
                "</html>";
    }




    public void sendQuatationParticularToUser(EmailReceiverInfo emailReceiverInfo, NeedEmailDetails needEmailDetails,
                                              QuatationParticularsAmount quatationParticularsAmount){
        try {
            String subject = "Pricing For User Household transfer ";
            String message = buildEmailContentForEndUser(emailReceiverInfo.getName(),needEmailDetails,quatationParticularsAmount);
            sendEmailService.sendEmail(emailReceiverInfo.getEmail(),subject,message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + emailReceiverInfo.getEmail());
            e.printStackTrace(); // or use logger
        }

    }

    private String buildEmailContentForEndUser(String userName,NeedEmailDetails needEmailDetails,
                                            QuatationParticularsAmount quatationParticularsAmount){

//        return "Dear, "+userName+"\n\n"
//           +" We are pleased to offer you to join our family MEX SERVICES (PACKERS AND  MOVERS  Div.)\n" +
//                   "We will safeguard your house holds for all the future ahead whenever required within city, within INDIA across the " +
//                   "world . With a close consent to you, we are quoting for our package to relocate your  HOUSEHOLD  and  CAR " +
//                   "from "+needEmailDetails.getSource()+" TO "+needEmailDetails.getDestination()+" under our STANDARD package.\n\n"
//           +"Packing Amount : "+quatationParticularsAmount.getPackingAmount()+"\n"
//           +"Loading Amount : "+quatationParticularsAmount.getLoadingAmount()+"\n"
//           +"Unpacking Amount : "+quatationParticularsAmount.getUnpackingAmount()+"\n"
//           +"Unloading Amount : "+quatationParticularsAmount.getUnloadingAmount()+"\n"
//           +"Packing And Loading Amount : "+quatationParticularsAmount.getPackingAndLoadingAmount()+"\n"
//           +"Unloading And Unpacking Amount : "+quatationParticularsAmount.getUnloadingAndUnpackingAmount()+"\n"
//           +"Packing,Loading,Unloading And Unpacking Amount : "+quatationParticularsAmount.getPackingAndLoadingAndUnloadingAndUnpackingAmount()+"\n"
//           +"Transportation Of Household Amount : "+quatationParticularsAmount.getTransportationOfHouseholdAmount()+"\n\n";

        return "<html>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "<p>Dear <strong>" + userName + "</strong>,</p>" +

                "<p>We are pleased to offer you the opportunity to join our family <strong>MEX SERVICES (PACKERS AND MOVERS Div.)</strong>.</p>" +

                "<p>We will safeguard your household items for all the future ahead, whenever required – within the city, across INDIA, and around the world.</p>" +

                "<p>With your consent, we are quoting our package to relocate your <strong>HOUSEHOLD</strong> and <strong>CAR</strong> from <strong>"
                + needEmailDetails.getSource() + "</strong> to <strong>" + needEmailDetails.getDestination() + "</strong> under our <strong>STANDARD</strong> package.</p>" +

                "<h4>Quotation Details:</h4>" +
                "<ul>" +
                "<li><strong>Packing Amount:</strong> " + quatationParticularsAmount.getPackingAmount() + "</li>" +
                "<li><strong>Loading Amount:</strong> " + quatationParticularsAmount.getLoadingAmount() + "</li>" +
                "<li><strong>Unpacking Amount:</strong> " + quatationParticularsAmount.getUnpackingAmount() + "</li>" +
                "<li><strong>Unloading Amount:</strong> " + quatationParticularsAmount.getUnloadingAmount() + "</li>" +
                "<li><strong>Packing and Loading Amount:</strong> " + quatationParticularsAmount.getPackingAndLoadingAmount() + "</li>" +
                "<li><strong>Unloading and Unpacking Amount:</strong> " + quatationParticularsAmount.getUnloadingAndUnpackingAmount() + "</li>" +
                "<li><strong>Packing, Loading, Unloading, and Unpacking Amount:</strong> " + quatationParticularsAmount.getPackingAndLoadingAndUnloadingAndUnpackingAmount() + "</li>" +
                "<li><strong>Transportation of Household Amount:</strong> " + quatationParticularsAmount.getTransportationOfHouseholdAmount() + "</li>" +
                "</ul>" +

                "<p>We look forward to serving you with utmost care and professionalism.</p>" +

                "<p>Warm regards,<br>" +
                "<strong>MEX SERVICES Team</strong></p>" +
                "</body>" +
                "</html>";

    }



    public void sendEmailToProjectExecutives(Lead lead,Quatation quatation,NeedEmailDetails needEmailDetails,List<QutationSelectedMasterForServiceResponse> selectedMasterForServiceResponses,String userEmail){
        try {
            String subject = "Quotation Reviewed – Ref No: " + lead.getLeadReferenceNo();
            String message = buildEmailForProjectTeam(lead,quatation,needEmailDetails,selectedMasterForServiceResponses);
            sendEmailService.sendEmail(userEmail, subject, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send pricing analysis email to: " + userEmail, e);
        }
    }



    private String buildEmailForProjectTeam(Lead l, Quatation q ,NeedEmailDetails n,List<QutationSelectedMasterForServiceResponse> qutationSelectedMasterForServiceResponse) {

        String selectedMaster = "";


        for (QutationSelectedMasterForServiceResponse p : qutationSelectedMasterForServiceResponse) {
            selectedMaster += "<table style=\"border-collapse: collapse; width: 100%; max-width: 600px;\" border=\"1\" cellpadding=\"8\">" +
                    "<tr><th align=\"left\">Service Type</th><td>" + p.getServiceType() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Provider Company Name</th><td>" + p.getCompanyName() + "</td></tr>" +
                    "<tr><th align=\"left\">Contact Person Name</th><td>" + p.getContactName() +"</td></tr>" +
                    "<tr><th align=\"left\">Contact Phone Number</th><td>" + p.getContactNumber() + "</td></tr>" +
                    "<tr><th align=\"left\">Email</th><td>" + p.getEmailId() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Sector</th><td>" + p.getServiceSector() + "</td></tr>" +
                    "<tr><th align=\"left\">Location</th><td>" + p.getLocation() + "</td></tr>" +
                    "<tr><th align=\"left\">Hub</th><td>" + p.getHub() + "</td></tr>" +
                    "<tr><th align=\"left\">State</th><td>" + p.getState() + "</td></tr>" +
                    "<tr><th align=\"left\">Grade</th><td>" + p.getGrade() + "</td></tr>" +
                    "</table><br>";
        }


        return "<html>" +
                "<body>" +
                "<p>Dear Project Team,</p>" +
                "<p>Service planning has been completed for the following quotation. Please proceed with execution.</p>" +


                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + q.getLead().getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Quotation Ref No</th><td>" + q.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + n.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + n.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + n.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + n.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + n.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + n.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + n.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + n.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + n.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + n.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + n.getCarMovingTime() + "</td></tr>" +
                "</table><br>" +


                "<p><strong>Selected Master For Services:</strong></p>" +
                selectedMaster +

                "<p>Kindly coordinate with the assigned service providers and take appropriate action.</p>" +
                "<p>Best regards,<br/>Mex Cargo Planning Team</p>" +
                "</body></html>";
    }


    public void sendEmailToAccountExecutive(Lead lead,Quatation quatation,NeedEmailDetails needEmailDetails,List<QutationSelectedMasterForServiceResponse> selectedMasterForServiceResponses,String userEmail){
        try {
            String subject = "Quotation Reviewed – Ref No: " + lead.getLeadReferenceNo();
            String message = buildEmailForAccountsTeam(quatation,needEmailDetails,selectedMasterForServiceResponses);
            sendEmailService.sendEmail(userEmail, subject, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send pricing analysis email to: " + userEmail, e);
        }
    }



    private String buildEmailForAccountsTeam(Quatation q, NeedEmailDetails n,List<QutationSelectedMasterForServiceResponse> serviceSelections) {
        String selectedMaster = "";


        for (QutationSelectedMasterForServiceResponse p : serviceSelections){
            selectedMaster += "<table style=\"border-collapse: collapse; width: 100%; max-width: 600px;\" border=\"1\" cellpadding=\"8\">" +
                    "<tr><th align=\"left\">Service Type</th><td>" + p.getServiceType() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Amount</th><td>" + p.getFinalServiceAmount() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Provider Company Name</th><td>" + p.getCompanyName() + "</td></tr>" +
                    "<tr><th align=\"left\">Contact Person Name</th><td>" + p.getContactName() +"</td></tr>" +
                    "<tr><th align=\"left\">Contact Phone Number</th><td>" + p.getContactNumber() + "</td></tr>" +
                    "<tr><th align=\"left\">Email</th><td>" + p.getEmailId() + "</td></tr>" +
                    "<tr><th align=\"left\">Service Sector</th><td>" + p.getServiceSector() + "</td></tr>" +
                    "<tr><th align=\"left\">Location</th><td>" + p.getLocation() + "</td></tr>" +
                    "<tr><th align=\"left\">Hub</th><td>" + p.getHub() + "</td></tr>" +
                    "<tr><th align=\"left\">State</th><td>" + p.getState() + "</td></tr>" +
                    "<tr><th align=\"left\">Grade</th><td>" + p.getGrade() + "</td></tr>" +
                    "</table><br>";
        }

        return "<html>" +
                "<body>" +
                "<p>Dear Accounts Team,</p>" +
                "<p>The following services have been planned and providers have submitted their final service costs:</p>" +

                "<table border='1' cellpadding='5' cellspacing='0'>" +
                "<tr><th>Lead Reference No</th><td>" + q.getLead().getLeadReferenceNo() + "</td></tr>" +
                "<tr><th>Quotation Ref No</th><td>" + q.getQuatationReferenceNo() + "</td></tr>" +
                "<tr><th>Origin</th><td>" + n.getSource() + "</td></tr>" +
                "<tr><th>Destination</th><td>" + n.getDestination() + "</td></tr>" +
                "<tr><th>Commodity</th><td>" + n.getCommodity() + "</td></tr>" +
                "<tr><th>Size</th><td>" + n.getSize() + "</td></tr>" +
                "<tr><th>Weight</th><td>" + n.getWeight() + "</td></tr>" +
                "<tr><th>Transportation Type</th><td>" + n.getTypeOfTransporatation() + "</td></tr>" +
                "<tr><th>Moving Date</th><td>" + n.getMovingDateAndTime() + "</td></tr>" +
                "<tr><th>Other Services</th><td>" + n.getOtherServices() + "</td></tr>" +
                "<tr><th>Car Name</th><td>" + n.getCarTransport() + "</td></tr>" +
                "<tr><th>Car Moving Date</th><td>" + n.getCarMovingDate() + "</td></tr>" +
                "<tr><th>Car Moving Time</th><td>" + n.getCarMovingTime() + "</td></tr>" +
                "</table><br>" +

                "<p><strong>Selected Master For Services:</strong></p>" +
                selectedMaster +

                "<p>Please process payment agreements or necessary follow-ups accordingly.</p>" +
                "<p>Best regards,<br/>Mex Cargo Planning Team</p>" +
                "</body></html>";
    }


}
