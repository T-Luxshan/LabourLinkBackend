package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.UserReportRepository;
import com.intelli5.labourlink.service.NotificationPollingService;
import com.intelli5.labourlink.service.UserReportService;
import com.intelli5.labourlink.utils.ReportRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class UserReportController {

    @Autowired
    UserReportService userReportService;
    @Autowired
    NotificationPollingService notificationPollingService;
    @PostMapping("/user")
    public ResponseEntity<ReportDTO> reportUser(@RequestBody ReportRequest reportRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            return ResponseEntity.ok(userReportService.addReport(currentPrincipalName, reportRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ReportDTO());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Integer id){
        return ResponseEntity.ok(userReportService.deleteReport(id));
    }

    @GetMapping("/getReportById/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Integer id){
        return ResponseEntity.ok(userReportService.getReportById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Integer id,
                                                  @RequestBody ReportRequest reportRequest){
        return ResponseEntity.ok(userReportService.updateReport(id, reportRequest));
    }

    @GetMapping("/allReports")
    public ResponseEntity<List<ReportDTO>> getAllReports(){
        return ResponseEntity.ok(userReportService.getAllReports());
    }
    @GetMapping("/allReports/admin")
    public ResponseEntity<List<ReportDTO>> getAllReportsForAdmin(){
        return ResponseEntity.ok(userReportService.getAllReportsForAdmin());
    }

    @GetMapping("/individual/{email}")
    public ResponseEntity<List <ReportDTO>> getReportByEmail(@PathVariable String email){
        return ResponseEntity.ok(userReportService.getReportByEmail(email));
    }
    @GetMapping("/poll")
    public ResponseEntity<List<ReportDTO>> pollForNewUserReport(@RequestParam(required = false) Integer lastCheckedReportedId) {
        List<ReportDTO> reports = notificationPollingService.pollForNewUserReport(lastCheckedReportedId);
        return ResponseEntity.ok(reports);
    }

//    @PostMapping("/send")
//    public void sendReport(@RequestBody UserReport notificationReport) {
//        userReportService.sendNotification(notificationReport);
//    }
//    @GetMapping(value = "/api/v1/report/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter streamReport() {
//        return userReportService.createEmitter();
//    }

}
