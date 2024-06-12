package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.service.BookingService;
import lombok.RequiredArgsConstructor;
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
}