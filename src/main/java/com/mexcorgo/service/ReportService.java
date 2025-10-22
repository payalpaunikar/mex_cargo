package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.ReportRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.ReportRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private ToDoRepository toDoRepository;

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ToDoRepository toDoRepository, ReportRepository reportRepository) {
        this.toDoRepository = toDoRepository;
        this.reportRepository = reportRepository;
    }

    public List<Report> addReportList(Integer counter, List<ReportRequest> reportRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getReportCount() !=0){
                List<Report> getListOfReport = reportRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<Report> newReportList = reportRequestList.stream()
                        .map(reportRequest -> {
                            Report report = new Report();
                            report = convertReportRequestToReport(report,reportRequest);
                            report.setToDo(getToDo);
                            return report;
                        }).toList();
                getToDo.setReportCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfReport.addAll(newReportList);

                reportRepository.saveAll(getListOfReport);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<Report> saveReportList = saveTodo.getReports();
                return saveReportList;
            }

            getToDo.setReportCount(counter);
            List<Report> reportList = reportRequestList.stream()
                    .map(reportRequest -> {
                        Report report = new Report();
                        report = convertReportRequestToReport(report,reportRequest);
                        report.setToDo(getToDo);
                        return report;
                    }).toList();

            reportRepository.saveAll(reportList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<Report> saveReportList = saveTodo.getReports();

            return saveReportList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(counter);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<Report> reportList = reportRequestList.stream()
                .map(reportRequest -> {
                    Report report = new Report();
                    report = convertReportRequestToReport(report,reportRequest);
                    report.setToDo(newToDo);
                    return report;
                }).toList();
        newToDo.setReports(reportList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<Report> saveReportList = saveTodo.getReports();
        return saveReportList;
    }


    public List<Report> getReportDetailsByToDoId(Long toDoId){
        List<Report> existingReportDetails = reportRepository.findByToDo_ToDoId(toDoId);
        return existingReportDetails;
    }


    public Report getReportDetailsById(Long reportId){
        Report report = reportRepository.findById(reportId)
                .orElseThrow(()-> new UserNotFoundException("report details with id : "+reportId+" is not found"));

        return report;
    }

    public Report updateReportDetails(Long reportId,ReportRequest reportRequest){
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(()-> new UserNotFoundException("report with id : "+reportId+" is not found"));

        existingReport.setName(reportRequest.getName());
        existingReport.setReportTime(reportRequest.getReportTime());

        Report saveReport = reportRepository.save(existingReport);

        return saveReport;
    }


    public String deleteReportDetailById(Long reportId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
        reportRepository.deleteById(reportId);
        existingToDo.setReportCount(existingToDo.getReportCount()-1);
        toDoRepository.save(existingToDo);
        return "Report Detail deleted succefully";
    }


    private Report convertReportRequestToReport(Report report,ReportRequest reportRequest){
       report.setName(reportRequest.getName());
       report.setReportTime(reportRequest.getReportTime());
       return report;
    }

}
