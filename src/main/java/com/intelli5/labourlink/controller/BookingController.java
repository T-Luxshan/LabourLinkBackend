package com.intelli5.labourlink.controller;

import com.google.api.Http;
import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.entity.Booking;
import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.service.BookingService;
import com.intelli5.labourlink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookLabour(@RequestBody  BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO bookingResponseDTO = bookingService.bookLabour(bookingRequestDTO);
        return ResponseEntity.ok(bookingResponseDTO);
    }

    @GetMapping("/customer/{customerEmail}")
    public  ResponseEntity<List<BookingHistoryDTO>> getBookingHistoryByCustomerId(@PathVariable String customerEmail){
        List<BookingHistoryDTO> bookingHistoryDTO=bookingService.getBookingHistoryByCustomerId(customerEmail);
        if (bookingHistoryDTO.isEmpty()) {
            return ResponseEntity.notFound().build(); // Handle empty list gracefully
        }
        return ResponseEntity.ok(bookingHistoryDTO);
    }

    @GetMapping("/labour/{labourEmail}")
    public ResponseEntity<List<BookingDetailsForLabourDTO>> getBookinDetailsByLabourId(@PathVariable String labourEmail){
        List<BookingDetailsForLabourDTO> bookingDetailsForLabourDTO=bookingService.getBookinDetailsByLabourId(labourEmail);
        if (bookingDetailsForLabourDTO.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDetailsForLabourDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetailsDTO> getFullBookingDetails(@PathVariable("id") Long id){
        BookingDetailsDTO bookingDetailsDTO=bookingService.getFullBookingDetails(id);
        return ResponseEntity.ok(bookingDetailsDTO);

    }


    @PatchMapping("/updateStage/{id}")
    public BookingStatusUpdateDTO updateBookingStage(@PathVariable Long id, @RequestBody BookingStatusUpdateDTO bookingStatusUpdateDTO){
        return bookingService.updateBookingStage(id, bookingStatusUpdateDTO.getBookingStage());
    }

    @GetMapping("/labour/{labourEmail}/{stage}")
    public ResponseEntity<List<BookingDetailsForLabourDTO>> getBookingByLabourIdAndStage(@PathVariable String labourEmail,
                                                                                        @PathVariable BookingStage stage
                                                                                        ){
        List<BookingDetailsForLabourDTO> bookingDetailsForLabourDTO=bookingService.getBookingByLabourIdAndStage(labourEmail, stage);
        if (bookingDetailsForLabourDTO.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDetailsForLabourDTO);
    }
    //------------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++-----------------------------------------------------------------
    //****------Booking :- Booking Pending table--------------------------------------
    @GetMapping("/pending")
    public ResponseEntity<List<BookingDTO>> getPendingAppointments() {
        try{
            List<BookingDTO> bookings = bookingService.getPendingAppointmentsWithDetails();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
    //***------Appointment :- Appointment Complete table------------------------
    @GetMapping("/deliver")
    public ResponseEntity<List<BookingDTO>> getCompleteAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getCompleteAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    //***------Appointment :- Appointment Accept table------------------------
    @GetMapping("/accept")
    public ResponseEntity<List<BookingDTO>> getAcceptAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getAcceptAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    //***------Appointment :- Appointment Declined table------------------------
    @GetMapping("/cancel")
    public ResponseEntity<List<BookingDTO>> getDeclinedAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getDeclinedAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    //***----------------------Booking - box 1- pending count------------------------
    @GetMapping("/pending_count")
    public ResponseEntity<Integer> getPendingAppointmentsCount() {
        List<BookingDTO> pendingAppointments = bookingService.getPendingAppointmentsWithDetails();
        int count = pendingAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //***----------------------Booking - box 2- Declined count------------------------
    @GetMapping("/declined_count")
    public ResponseEntity<Integer> getCancelAppointmentsCount() {
        List<BookingDTO> cancelAppointments = bookingService.getDeclinedAppointmentsWithDetails();
        int count = cancelAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //***----------------------Appointment - box 3- Accept count------------------------
    @GetMapping("/accept_count")
    public ResponseEntity<Integer> getAcceptAppointmentsWithDetailsCount() {
        List<BookingDTO> acceptAppointments = bookingService.getAcceptAppointmentsWithDetails();
        int count = acceptAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //***----------------------Appointment - box 4- complete count------------------------
    @GetMapping("/complete_count")
    public ResponseEntity<Integer> getCompleteAppointmentsWithDetailsCount() {
        List<BookingDTO> completeAppointments = bookingService.getCompleteAppointmentsWithDetails();
        int count = completeAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //***-------------------------------------to dash board total Appointments --------------------------------
    @GetMapping("/total_app")
    public ResponseEntity<Integer> getTotalAppointmentsCount() {
        List<BookingDTO> cancelAppointments = bookingService.getDeclinedAppointmentsWithDetails();
        List<BookingDTO> completeAppointments = bookingService.getCompleteAppointmentsWithDetails();
        List<BookingDTO> pendingAppointments = bookingService.getPendingAppointmentsWithDetails();
        List<BookingDTO> acceptAppointments = bookingService.getAcceptAppointmentsWithDetails();
        int totalAppointmentsCount = cancelAppointments.size() + completeAppointments.size() + pendingAppointments.size()+acceptAppointments.size();
        return new ResponseEntity<>(totalAppointmentsCount, HttpStatus.OK);
    }

    //***--------------------------Booking : -graph : -Job Vs  Booking count along with booking stage--------------------------------
    @GetMapping("/graphleft")
    public ResponseEntity<List<Map<String,Object>>> getJobVsTotalAppointment() {
        List<Map<String,Object>>  graphData=bookingService.jobVsTotalAppointment();
        return new ResponseEntity<>(graphData, HttpStatus.OK);
    }

//    //------------------------------------Booking-graph right : -Cancelled job Vs Total Booking-----------------------
//    @GetMapping("/graphright")
//    public List<Object[]> findCancelledBookCounts() {
//        return bookingService.findCancelledBookCounts();
//    }

    //------------------------------------Dashboard -graph  : - Active customer vs day-------------------------------
    @GetMapping("/dashboard/g_active")
    public List<Object[]> findActiveCustomerCount() {
        return bookingService.findActiveCustomerCount();
    }

    //------------------------------------Dashboard -graph  : - Active labour vs day-------------------------------
    @GetMapping("/dashboard/g_active_l")
    public List<Object[]> findActiveLabourCount() {
        return bookingService.findActiveLabourCount();
    }

    //------------------------------------Dashboard -line graph  : - Appointment count vs day---------------------------

    @GetMapping("/dashboard/g_AppCount")
    public List<Object[]> findSuccessfulBookingWithDay() {
        return bookingService.findSuccessfulBookingWithDay();
    }

//    //-----------------------------------User: Individual -User Individiual booking history------------------------------------
//    @GetMapping("/labour/{email}")
//    public ResponseEntity<List<BookingIndividualDTO >> getLabourCompleteBookingById(@PathVariable String email){
//        try{
//            List<BookingIndividualDTO> bookingDetailLabour= bookingService.getLabourCompleteBookingById(email);
//            return new ResponseEntity<>(bookingDetailLabour,HttpStatus.OK) ;
//        }catch(RuntimeException e){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

    //------------Job-02`(pie)--Booking count for each jobroles :According to the complete,accept,declined,pending------------------------
    @GetMapping("/bookingcount")
    public ResponseEntity<List<BookingCountDTO>> getBookingCountWithJobRole(){
        List<BookingCountDTO> counts=bookingService.getBookingCountWithJobRole();
        return new ResponseEntity<>(counts,HttpStatus.OK);
    }
    //----------------JOB uselinechart 3 in 1--Booking count for each jobroles : pending------------------------
    @GetMapping("/pendingbookingcount")
    public ResponseEntity<List<BookingCountDTO>> getPendingCountWithJob(){
        List<BookingCountDTO> counts=bookingService.getPendingCountWithJob();
        return new ResponseEntity<>(counts,HttpStatus.OK);
    }
    //----------------Job03--Booking count for each jobroles :According to the declined------------------------
    @GetMapping("/declinedbookingcount")
    public ResponseEntity<List<BookingCountDTO>> getDeclinedCountWithJob(){
        List<BookingCountDTO> counts=bookingService.getDeclinedCountWithJob();
        return new ResponseEntity<>(counts,HttpStatus.OK);
    }
    //----------------Job03--Booking count for each jobroles :According to the accept------------------------
    @GetMapping("/acceptbookingcount")
    public ResponseEntity<List<BookingCountDTO>> getAcceptCountWithJob(){
        List<BookingCountDTO> counts=bookingService.getAcceptCountWithJob();
        return new ResponseEntity<>(counts,HttpStatus.OK);
    }
    //----------------Job03--Booking count for each jobroles :According to the complete------------------------
    @GetMapping("/completebookingcount")
    public ResponseEntity<List<BookingCountDTO>> getCompleteCountWithJob(){
        List<BookingCountDTO> counts=bookingService.getCompleteCountWithJob();
        return new ResponseEntity<>(counts,HttpStatus.OK);
    }
    //    ------------------------------User:-User detail individual appointment detail fetching -----------------
    @GetMapping("/booking/{email}")
    public ResponseEntity<List<BookingIndividualDTO>> findBookingById(@PathVariable String email) {
        try {
            List<BookingIndividualDTO> bookings = bookingService.findBookingById(email);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //***------------------Booking : High demand job role ------------------------
    @GetMapping("/highdemand")
    public ResponseEntity<JobRole> getDemandedJob(){
        JobRole job=bookingService.getDemandedJob();
        return new ResponseEntity<>(job,HttpStatus.OK);
    }
}