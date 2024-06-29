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
import java.util.*;
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
                    .id(bookingDetails.getId())
                    .bookingStage(bookingDetails.getBookingStage())
                    .appointmentDate(bookingDetails.getDate())
                    .appointmentTime(bookingDetails.getStartTime())
                    .jobRole(bookingDetails.getJobRole())
                    .customerId(bookingDetails.getCustomer().getEmail())
                    .customerName(bookingDetails.getCustomer().getName())
                    .labourId(bookingDetails.getLabour().getEmail())
                    .labourName(bookingDetails.getLabour().getName())
                    .description(bookingDetails.getJobDescription())
                    .amount(bookingDetails.getAmount())
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
                .labourId(booking.getLabour().getEmail())
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
                .customerEmail((booking.getCustomer().getEmail()))
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


    public List<BookingDetailsDTO> findCompletedBookings(String customerEmail) {
        List<Booking> bookings = bookingRepository.findCompletedBookings(customerEmail);
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BookingDetailsDTO convertToDTO(Booking booking) {
        return BookingDetailsDTO.builder()
                .id(booking.getId())
                .bookingStage(booking.getBookingStage())
                .appointmentDate(booking.getDate())
                .appointmentTime(booking.getStartTime())
                .jobRole(booking.getJobRole())
                .customerId(booking.getCustomer().getEmail().toString())
                .labourId(booking.getLabour().getEmail().toString())
                .customerName(booking.getCustomer().getName())
                .labourName(booking.getLabour().getName())
                .amount(booking.getAmount())
                .build();
    }

    public BookingDetailsDTO updateBookingAmount(Long id,BookingDetailsDTO bookingDetailsDTO){
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setAmount(bookingDetailsDTO.getAmount());
            Booking updatedBooking = bookingRepository.save(booking);

            return BookingDetailsDTO.builder()
                    .id(booking.getId())
                    .bookingStage(booking.getBookingStage())
                    .appointmentDate(booking.getDate())
                    .appointmentTime(booking.getStartTime())
                    .jobRole(booking.getJobRole())
                    .customerId(booking.getCustomer().getEmail())
                    .labourId(booking.getLabour().getEmail())
                    .customerName(booking.getCustomer().getName())
                    .labourName(booking.getLabour().getName())
                    .amount(booking.getAmount())
                    .description(booking.getJobDescription())
                    .build();
        }
        throw new RuntimeException("Booking not found with id " + id);

    }
//-------------------+++++++++++++++++++++++++++++++++++++++++------------------------------------------------------------

    //***------Booking :- Booking Pending table--------------------------------------
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
                bookingsDto.setJobRole(JobRole.valueOf(booking.getJobRole().toString()));
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
    //***------Booking :- Booking accept table--------------------------------------

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
    //***------Booking :- Booking declined table--------------------------------------

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

    //***-----------------------------------Booking : -graph left : -Job Vs Total Booking--------------------------------
    public List<Map<String,Object>> jobVsTotalAppointment() {
        List<Object[]> results = bookingRepository.jobVsTotalAppointment();
        List<Map<String, Object>> formattedResults = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("jobRole", result[0]);
            map.put("totalCount", result[1]);
            map.put("declinedCount", result[2]);
            map.put("pendingCount", result[3]);
            map.put("acceptedCount", result[4]);
            map.put("completedCount", result[5]);

            formattedResults.add(map);
        }
        return formattedResults;
    }
    public List<Object[]> findActiveCustomerCount(LocalDate startDate) {

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
    public List<BookingIndividualDTO> findBookingById(String email) {
        List<Booking> bookings = bookingRepository.findByLabourEmail(email);

        return bookings.stream()
                .filter(booking -> booking.getBookingStage() == BookingStage.COMPLETED)
                .map(this::mapToBookingIndividualDTO)
                .collect(Collectors.toList());
    }
    private BookingIndividualDTO mapToBookingIndividualDTO(Booking booking) {
        BookingIndividualDTO dto = BookingIndividualDTO.builder()
                .customer(booking.getCustomer().getEmail())
                .jobRole(booking.getJobRole())
                .jobDescription(booking.getJobDescription())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .build();

        return dto;
    }
    public JobRole getDemandedJob() {
        return bookingRepository.getDemandedJob();
    }

    public Map<JobRole, Integer> countBookingByJobRole() {
        List<Object[]> results = bookingRepository.countBookingByJobRole();
        Map<JobRole, Integer> jobRoleCounts = new HashMap<>();

        for (Object[] result : results) {
            if (result[0] != null && result[1] != null) {
                JobRole jobRole = (JobRole) result[0]; // Directly cast to JobRole
                Long countLong = (Long) result[1];
                jobRoleCounts.put(jobRole, countLong.intValue());
            }
        }

        return jobRoleCounts;
    }


}