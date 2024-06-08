package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.BookingRequestDTO;
import com.intelli5.labourlink.dto.BookingResponseDTO;
import com.intelli5.labourlink.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
