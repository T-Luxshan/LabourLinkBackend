package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.BookingRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public BookingStatusUpdateDTO updateBookingStage(Long id, BookingStage bookingStage) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setBookingStage(bookingStage);
            Booking updatedBooking = bookingRepository.save(booking);

            return BookingStatusUpdateDTO.builder()
                    .bookingStage(updatedBooking.getBookingStage())
                    .build();
        }
        throw new RuntimeException("Booking not found with id " + id);
    }

    public BookingDetailsDTO getFullBookingDetails(Long id){
        Optional<Booking> booking=bookingRepository.findById(id);
        if(booking.isPresent()){
            Booking bookingDetails = booking.get();

            return BookingDetailsDTO.builder()
                    .bookingStage(bookingDetails.getBookingStage())
                    .appointmentDate(bookingDetails.getDate())
                    .appointmentTime(bookingDetails.getStartTime())
                    .jobRole(bookingDetails.getJobRole())
                    .customerId(bookingDetails.getCustomer().getEmail())
                    .customerName(bookingDetails.getCustomer().getName())
                    .labourId(bookingDetails.getLabour().getEmail())
                    .labourName(bookingDetails.getLabour().getName())
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

    //-------------------+++++++++++++++++++++++++++++++++++++++++------------------------------------------------------------
    //------Booking :- Booking Pending table--------------------------------------
    public List<BookingDTO> getPendingAppointmentsWithDetails() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingsDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getBookingStage() == BookingStage.PENDING) {
                BookingDTO bookingsDto = new BookingDTO();
                bookingsDto.setId(booking.getId());
                bookingsDto.setCustomerName(booking.getCustomer().getName());
                bookingsDto.setCustomerEmail(booking.getCustomer().getEmail());
                bookingsDto.setLabourName(booking.getLabour().getName());
                bookingsDto.setJobRole(booking.getJobRole());
                bookingsDto.setBookingMadeDate(booking.getBookingMadeDate());

                bookingsDtos.add(bookingsDto);
            }
        }
        return bookingsDtos;
    }
//------Booking :- Booking complete table--------------------------------------

    public List<BookingDTO> getCompleteAppointmentsWithDetails() {
        List<Booking> bookings = bookingRepository.findAll(); // Fetch bookings from repository
        List<BookingDTO> bookingsDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getBookingStage() == BookingStage.COMPLETED) {
                BookingDTO bookingsDto = new BookingDTO();
                bookingsDto.setId(booking.getId());
                bookingsDto.setCustomerName(booking.getCustomer().getName());
                bookingsDto.setCustomerEmail(booking.getCustomer().getEmail());
                bookingsDto.setLabourName(booking.getLabour().getName());
                bookingsDto.setJobRole(booking.getJobRole());
                bookingsDto.setBookingMadeDate(booking.getBookingMadeDate());

                bookingsDtos.add(bookingsDto);
            }
        }
        return bookingsDtos;
    }
    //------Booking :- Booking accept table--------------------------------------

    public List<BookingDTO> getAcceptAppointmentsWithDetails() {
        List<Booking> bookings = bookingRepository.findAll(); // Fetch bookings from repository
        List<BookingDTO> bookingsDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getBookingStage() == BookingStage.ACCEPTED) {
                BookingDTO bookingsDto = new BookingDTO();
                bookingsDto.setId(booking.getId());
                bookingsDto.setCustomerName(booking.getCustomer().getName());
                bookingsDto.setCustomerEmail(booking.getCustomer().getEmail());
                bookingsDto.setLabourName(booking.getLabour().getName());
                bookingsDto.setJobRole(booking.getJobRole());
                bookingsDto.setBookingMadeDate(booking.getBookingMadeDate());

                bookingsDtos.add(bookingsDto);
            }
        }
        return bookingsDtos;
    }
    //------Booking :- Booking declined table--------------------------------------

    public List<BookingDTO> getDeclinedAppointmentsWithDetails() {
        List<Booking> bookings = bookingRepository.findAll(); // Fetch bookings from repository
        List<BookingDTO> bookingsDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getBookingStage() == BookingStage.DECLINED) {
                BookingDTO bookingsDto = new BookingDTO();
                bookingsDto.setId(booking.getId());
                bookingsDto.setCustomerName(booking.getCustomer().getName());
                bookingsDto.setCustomerEmail(booking.getCustomer().getEmail());
                bookingsDto.setLabourName(booking.getLabour().getName());
                bookingsDto.setJobRole(booking.getJobRole());
                bookingsDto.setBookingMadeDate(booking.getBookingMadeDate());

                bookingsDtos.add(bookingsDto);
            }
        }
        return bookingsDtos;
    }

    //-----------------------------------Booking : -graph left : -Job Vs Total Booking--------------------------------
    public List<Object[]> jobVsTotalAppointment() {
        List<Object[]> results = bookingRepository.findJobBookingCounts();
        return results.stream()
                .map(result -> new Object[]{result[0], result[1] == null ? 0L : result[1]})
                .collect(Collectors.toList());
    }

    //-----------------------------------Booking : -graph right : -Job Vs cancelled Total Booking--------------------------------
    public List<Object[]> findCancelledBookCounts() {
        List<Object[]> results = bookingRepository.findCancelledBookCounts();
        return results.stream()
                .map(result -> new Object[]{result[0], result[1] == null ? 0L : result[1]})
                .collect(Collectors.toList());
    }

    public List<Object[]> findActiveCustomerCount() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        return bookingRepository.findActiveCustomerCount(startDate);
    }

    public List<Object[]> findActiveLabourCount() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        return bookingRepository.findActiveLabourCount(startDate);
    }

    public List<Object[]> findSuccessfulBookingWithDay() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        return bookingRepository.findSuccessfulBookingWithDay(startDate);
    }

//    public List<BookingIndividualDTO> getLabourCompleteBookingById(String email) {
//        Optional<User> labour = labourRepository.findByEmail(email);
//        List<Booking> bookings = bookingRepository.findByLabour(labour);
//        return bookings.stream()
//                .map(this::convertToBookingIndividualDTO)
//                .collect(Collectors.toList());
//    }
//    private BookingIndividualDTO convertToBookingIndividualDTO(Booking booking) {
//        return new BookingIndividualDTO(
//                booking.getCustomer(),
//                booking.getJobRole(),
//                booking.getJobDescription(),
//                booking.getDate(),
//                booking.getStartTime()
//        );
//    }

    public List<BookingCountDTO> getLabourRoleCount() {
        return bookingRepository.getLabourRoleCount();
    }

    public List<BookingCountDTO> getBookingCountWithJobRole() {
        return bookingRepository.getBookingCountWithJobRole();
    }

    public List<BookingCountDTO> getPendingCountWithJob() {
        return bookingRepository.getPendingCountWithJob();
    }

    public List<BookingCountDTO> getDeclinedCountWithJob() {
        return bookingRepository.getDeclinedCountWithJob();
    }
    public List<BookingCountDTO> getAcceptCountWithJob() {
        return bookingRepository.getAcceptCountWithJob();
    }
    public List<BookingCountDTO> getCompleteCountWithJob() {
        return bookingRepository.getCompleteCountWithJob();
    }

    public List<BookingIndividualDTO> findBookingById(String email) {
        List<Booking> bookings = bookingRepository.findByLabourEmail(email);

        return bookings.stream()
                .filter(booking -> booking.getBookingStage() == BookingStage.COMPLETED)
                .map(this::mapToBookingIndividualDTO)
                .collect(Collectors.toList());
    }

    private BookingIndividualDTO mapToBookingIndividualDTO(Booking booking) {
        BookingIndividualDTO dto = BookingIndividualDTO.builder()
                .customer(booking.getCustomer())
                .jobRole(booking.getJobRole())
                .jobDescription(booking.getJobDescription())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .build();

        return dto;
    }


    //------------------------Individual bookings history ----------------------------------------------


}