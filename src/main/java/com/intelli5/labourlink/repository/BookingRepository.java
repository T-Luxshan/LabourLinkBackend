package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByLabourEmail(String email);

    @Query("select b from  Booking b where b.bookingStage=?2 and b.labour=?1")
    List<Booking> getBookingByLabourIdAndStage(Labour labour, BookingStage stage);

    //-----------------++++++++++++++++++++++++++++++++++++++++---------------------------------------
    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByLabour(Optional<User> labour);

    //Booking graph :1 ----------------------------
    @Query("select b.jobRole, COUNT(b.id) " +
            "from Booking b " +
            "group by b.jobRole")
    List<Object[]> findJobBookingCounts();

    //Booking declined/cancelled  graph :2 ----------------------------
    @Query("select b.jobRole, COUNT(b.id) " +
            "from Booking b " +
            "where b.bookingStage=com.intelli5.labourlink.entity.BookingStage.DECLINED " +
            "group by b.jobRole")
    List<Object[]> findCancelledBookCounts();

    //Booking Accept : 3-------------------------
    @Query("select b.jobRole, COUNT(b.id) " +
            "from Booking b " +
            "where b.bookingStage=com.intelli5.labourlink.entity.BookingStage.ACCEPTED " +
            "group by b.jobRole")
    List<Object[]> findAcceptBookCounts();
    //Booking Complete : 4-------------------------
    @Query("select b.jobRole, COUNT(b.id) " +
            "from Booking b " +
            "where b.bookingStage=com.intelli5.labourlink.entity.BookingStage.COMPLETED " +
            "group by b.jobRole")
    List<Object[]> findCompleteBookCounts();
    //Booking Pending : 4-------------------------
    @Query("select b.jobRole, COUNT(b.id) " +
            "from Booking b " +
            "where b.bookingStage=com.intelli5.labourlink.entity.BookingStage.PENDING " +
            "group by b.jobRole")
    List<Object[]> findPendingBookCounts();

    //Dashboard graph :1 ----------------------------
    @Query(
            "select dayname(b.bookingMadeDate) AS day_of_week, count(b.customer) AS total_Customer " +
                    "from Booking b " +
                    "where b.bookingMadeDate > :startDate " +
                    "group by dayname(b.bookingMadeDate), b.bookingMadeDate " +
                    "order by field(dayname(b.bookingMadeDate), 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday' ,'Friday', 'Saturday')"
    )
    List<Object[]> findActiveCustomerCount(@Param("startDate") LocalDate startDate);

    //Dashboard graph :2 ----------------------------
    @Query(
            "select dayname(b.date) AS day_of_week, count(b.labour) AS total_Labour " +
                    "from Booking b " +
                    "where b.date > :startDate  and b.bookingStage=com.intelli5.labourlink.entity.BookingStage.ACCEPTED "  +
                    "group by dayname(b.date), b.date " +
                    "order by field(dayname(b.date), 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday' ,'Friday', 'Saturday')"
    )
    List<Object[]> findActiveLabourCount(@Param("startDate") LocalDate startDate);

    //Dashboard graph :3 ----------------------------
    @Query(
            "select dayname(b.bookingMadeDate) AS day_of_week, count(b.id) AS total_appointments " +
                    "from Booking b " +
                    "where b.bookingMadeDate > :startDate  and b.bookingStage=com.intelli5.labourlink.entity.BookingStage.COMPLETED " +
                    "group by dayname(b.bookingMadeDate), b.bookingMadeDate " +
                    "order by field(dayname(b.bookingMadeDate), 'Sun', 'Mon', 'Tues', 'Wed', 'Thur' ,'Fri', 'Sat')"
    )
    List<Object[]> findSuccessfulBookingWithDay(@Param("startDate") LocalDate startDate);

}
