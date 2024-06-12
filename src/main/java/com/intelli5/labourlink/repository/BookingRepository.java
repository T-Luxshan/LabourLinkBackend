package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByLabourEmail(String email);
}
