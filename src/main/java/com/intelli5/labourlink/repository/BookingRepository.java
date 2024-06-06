package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
