package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.dto.BookingHistoryDTO;
import com.intelli5.labourlink.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByLabourEmail(String email);
}
