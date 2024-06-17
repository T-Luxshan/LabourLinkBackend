package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.entity.Booking;
import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    //------Booking :- Booking complete table--------------------------------------
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getPendingAppointments() {
        List<BookingDTO> bookings = bookingService.getPendingAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    //------Appointment :- Appointment Complete table------------------------
    @GetMapping("/deliver")
    public ResponseEntity<List<BookingDTO>> getDeliveredAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getDeliveredAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    //------Appointment :- Appointment Accept table------------------------
    @GetMapping("/accept")
    public ResponseEntity<List<BookingDTO>> getAcceptAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getAcceptAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    //------Appointment :- Appointment Declined table------------------------
    @GetMapping("/cancel")
    public ResponseEntity<List<BookingDTO>> getCancelAppointmentsWithDetails() {
        List<BookingDTO> bookings = bookingService.getCancelAppointmentsWithDetails();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }



    //----------------------Appointment - box 1- pending count------------------------
    @GetMapping("/pending_count")
    public ResponseEntity<Integer> getPendingAppointmentsCount() {
        List<BookingDTO> pendingAppointments = bookingService.getPendingAppointmentsWithDetails();
        int count = pendingAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //----------------------Appointment - box 3- delivered count------------------------

    @GetMapping("/delivered_count")
    public ResponseEntity<Integer> getDeliveredAppointmentsWithDetailsCount() {
        List<BookingDTO> deliveredAppointments = bookingService.getDeliveredAppointmentsWithDetails();
        int count = deliveredAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
//----------------------Appointment - Revenue box 2-------------------------

    @GetMapping("/cancel_count")
    public ResponseEntity<Integer> getCancelAppointmentsWithDetailsCount() {
        List<BookingDTO> cancelAppointments = bookingService.getCancelAppointmentsWithDetails();
        int count = cancelAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //-------------------------------------to dash board total Appointments --------------------------------
    @GetMapping("/total_app")
    public ResponseEntity<Integer> getTotalAppointmentsCount() {
        List<BookingDTO> cancelAppointments = bookingService.getCancelAppointmentsWithDetails();
        List<BookingDTO> deliveredAppointments = bookingService.getDeliveredAppointmentsWithDetails();
        List<BookingDTO> pendingAppointments = bookingService.getPendingAppointmentsWithDetails();
        int totalAppointmentsCount = cancelAppointments.size() + deliveredAppointments.size() + pendingAppointments.size();
        return new ResponseEntity<>(totalAppointmentsCount, HttpStatus.OK);
    }

    //-----------------------------------Booking : -graph left : -Job Vs Total Booking--------------------------------
    @GetMapping("/graphleft")
    public List<Object[]> getJobVsTotalAppointment() {
        return bookingService.jobVsTotalAppointment();
    }

    //------------------------------------Booking-graph right : -Cancelled job Vs Total Booking-----------------------
    @GetMapping("/graphright")
    public List<Object[]> findCancelledBookCounts() {
        return bookingService.findCancelledBookCounts();
    }

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

    //------------------------------------User Individiual booking history------------------------------------
    @GetMapping("/labour/{email}")
    public ResponseEntity<List<BookingIndividualDTO >> getLabourCompleteBookingById(@PathVariable String email){
        try{
            List<BookingIndividualDTO> bookingDetailLabour= bookingService.getLabourCompleteBookingById(email);
            return new ResponseEntity<>(bookingDetailLabour,HttpStatus.OK) ;
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


}