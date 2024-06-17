package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.BookingRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    private final BookingRepository bookingRepository;
    private final LabourRepository labourRepository;
    private final CustomerRepository customerRepository;

    public BookingResponseDTO bookLabour(BookingRequestDTO bookingRequestDTO) {
        Optional<User> labourOptional = labourRepository.findById(bookingRequestDTO.getLabourId());
        Optional<User> customerOptional = customerRepository.findById(bookingRequestDTO.getCustomerId());

        if (labourOptional.isPresent() && customerOptional.isPresent()) {
            Labour labour = (Labour) labourOptional.get();
            Customer customer = (Customer) customerOptional.get();

            Booking booking = Booking.builder()
                    .labour(labour)
                    .customer(customer)
                    .date(bookingRequestDTO.getDate())
                    .startTime(bookingRequestDTO.getStartTime())
                    .bookingStage(bookingRequestDTO.getBookingStage())
                    .jobDescription(bookingRequestDTO.getJobDescription())
                    .jobRole(bookingRequestDTO.getJobRole())
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            return convertToBookingResponseDTO(savedBooking);

        } else {
            throw new RuntimeException("Labour or Customer not found");
        }
    }

    public List<BookingHistoryDTO> getBookingHistoryByCustomerId(String email) {
        List<Booking> bookings = bookingRepository.findByCustomerEmail(email);
        if (bookings.isEmpty()) {
            return Collections.emptyList(); // Return empty list if no bookings found
        }
        return bookings.stream()
                .map(booking -> convertToBookingHistoryDTO(booking))
                .collect(Collectors.toList());
    }

    public List<BookingDetailsForLabourDTO> getBookinDetailsByLabourId(String email) {
        List<Booking> bookings = bookingRepository.findByLabourEmail(email);
        if (bookings.isEmpty()) {
            return Collections.emptyList();
        }
        return bookings.stream().map(booking -> convertToBookingDetailsForLabourDTO(booking))
                .collect(Collectors.toList());
    }

    public BookingStatusUpdateDTO updateBookingStage(Long id, BookingStage bookingStage){
        Optional<Booking> bookingOptional=bookingRepository.findById(id);
        if (bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setBookingStage(bookingStage);
            Booking updatedBooking = bookingRepository.save(booking);

            return BookingStatusUpdateDTO.builder()
                    .bookingStage(updatedBooking.getBookingStage())
                    .build();
        }
        throw new RuntimeException("Booking not found with id " + id);
    }



    private BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .id(booking.getId())
                .labourId(booking.getLabour().getEmail())
                .customerId(booking.getCustomer().getEmail())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .bookingStage(booking.getBookingStage())
                .jobDescription(booking.getJobDescription())
                .jobRole(booking.getJobRole())
                .build();
    }

    private BookingHistoryDTO convertToBookingHistoryDTO(Booking booking) {

        return BookingHistoryDTO.builder()
                .id(booking.getId())
                .labourName(booking.getLabour().getName())
                .jobRole(booking.getJobRole())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .bookingStage(booking.getBookingStage())
                .jobDescription(booking.getJobDescription())
                .build();
    }

    private BookingDetailsForLabourDTO convertToBookingDetailsForLabourDTO(Booking booking) {
        return BookingDetailsForLabourDTO.builder()
                .id(booking.getId())
                .jobRole(booking.getJobRole())
                .customerName(booking.getCustomer().getName())
                .bookingStage(booking.getBookingStage())
                .jobDescription(booking.getJobDescription())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .build();
    }

    public List<BookingDetailsForLabourDTO> getBookingByLabourIdAndStage(String labourEmail, BookingStage stage) {
        Labour labour = labourRepository.findLabour(labourEmail);
        List<Booking> bookings = bookingRepository.getBookingByLabourIdAndStage(labour, stage);
        if (bookings.isEmpty()) {
            return Collections.emptyList();
        }
        return bookings.stream().map(booking -> convertToBookingDetailsForLabourDTO(booking))
                .collect(Collectors.toList());
    }
}
