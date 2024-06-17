package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Booking;
import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByLabourEmail(String email);

    @Query("select b from  Booking b where b.bookingStage=?2 and b.labour=?1")
    List<Booking> getBookingByLabourIdAndStage(Labour labour, BookingStage stage);
}
