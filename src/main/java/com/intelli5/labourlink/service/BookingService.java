package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.BookingRequestDTO;
import com.intelli5.labourlink.dto.BookingResponseDTO;
import com.intelli5.labourlink.entity.Booking;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.BookingRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

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
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            return convertToBookingResponseDTO(savedBooking);

        } else {
            throw new RuntimeException("Labour or Customer not found");
        }
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
                .build();
    }
}
