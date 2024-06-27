package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.dto.BookingCountDTO;
import com.intelli5.labourlink.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByLabourEmail(String email);

    @Query("select b from  Booking b where b.bookingStage=?2 and b.labour=?1")
    List<Booking> getBookingByLabourIdAndStage(Labour labour, BookingStage stage);


    @Query("SELECT b FROM Booking b WHERE b.bookingStage = com.intelli5.labourlink.entity.BookingStage.COMPLETED AND b.customer.email = :email")
    List<Booking> findCompletedBookings(String email);

    //-----------------++++++++++++++++++++++++++++++++++++++++---------------------------------------
    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByLabour(Optional<User> labour);

    //Booking graph :1 ----------------------------
    @Query("select b.jobRole , " +
            "COUNT(b.id) as totalCount , " +
            "sum (case when b.bookingStage =com.intelli5.labourlink.entity.BookingStage.DECLINED then 1 else 0 end ) as declined_count , " +
            "sum (case when b.bookingStage =com.intelli5.labourlink.entity.BookingStage.PENDING then 1 else 0 end ) as pending_count , " +
            "sum (case when b.bookingStage =com.intelli5.labourlink.entity.BookingStage.ACCEPTED then 1 else 0 end ) as accept_count , " +
            "sum (case when b.bookingStage =com.intelli5.labourlink.entity.BookingStage.COMPLETED then 1 else 0 end ) as complete_count  " +
            "from Booking b " +
            "group by b.jobRole")
    List<Object[]> jobVsTotalAppointment();

    //***---Dashboard graph :1 ----------------------------
    @Query(
            "select FORMAT(b.bookingMadeDate, 'dddd') AS day_of_week, count(b.customer) AS total_customer " +
                    "from Booking b " +
                    "where b.bookingMadeDate > :startDate " +
                    "group by FORMAT(b.bookingMadeDate, 'dddd') " +
                    "order by " +
                    "CASE FORMAT(b.bookingMadeDate, 'dddd') " +
                    "    WHEN 'Sunday' THEN 1 " +
                    "    WHEN 'Monday' THEN 2 " +
                    "    WHEN 'Tuesday' THEN 3 " +
                    "    WHEN 'Wednesday' THEN 4 " +
                    "    WHEN 'Thursday' THEN 5 " +
                    "    WHEN 'Friday' THEN 6 " +
                    "    WHEN 'Saturday' THEN 7 " +
                    "END"
    )
    List<Object[]> findActiveCustomerCount(@Param("startDate") LocalDate startDate);

    //**--Dashboard graph :2 ----------------------------
    @Query(
            "select FORMAT(b.date, 'dddd') AS day_of_week, count(b.labour) AS total_labour " +
                    "from Booking b " +
                    "where b.date > :startDate and b.bookingStage = com.intelli5.labourlink.entity.BookingStage.ACCEPTED " +
                    "group by FORMAT(b.date, 'dddd') " +
                    "order by " +
                    "CASE FORMAT(b.date, 'dddd') " +
                    "    WHEN 'Sunday' THEN 1 " +
                    "    WHEN 'Monday' THEN 2 " +
                    "    WHEN 'Tuesday' THEN 3 " +
                    "    WHEN 'Wednesday' THEN 4 " +
                    "    WHEN 'Thursday' THEN 5 " +
                    "    WHEN 'Friday' THEN 6 " +
                    "    WHEN 'Saturday' THEN 7 " +
                    "END"
    )
    List<Object[]> findActiveLabourCount(@Param("startDate") LocalDate startDate);

    //Dashboard graph :3 ----------------------------
    @Query(
            "select FORMAT(b.bookingMadeDate, 'dddd') AS day_of_week, count(b.id) AS total_appointments " +
                    "from Booking b " +
                    "where b.bookingMadeDate > :startDate and b.bookingStage = com.intelli5.labourlink.entity.BookingStage.COMPLETED " +
                    "group by FORMAT(b.bookingMadeDate, 'dddd') " +
                    "order by CASE " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Sunday' THEN 1 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Monday' THEN 2 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Tuesday' THEN 3 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Wednesday' THEN 4 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Thursday' THEN 5 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Friday' THEN 6 " +
                    "    WHEN FORMAT(b.bookingMadeDate, 'dddd') = 'Saturday' THEN 7 " +
                    "END"
    )
    List<Object[]> findSuccessfulBookingWithDay(@Param("startDate") LocalDate startDate);



//    //------------------Booking count for each jobroles :According to the complete,accept,declined,pending------------------------
//    @Query(
//            "select  (b.jobRole ,COUNT(DISTINCT b.id)) " +
//            "from Booking b " +
//            "where b.bookingStage IN (com.intelli5.labourlink.entity.BookingStage .COMPLETED , com.intelli5.labourlink.entity.BookingStage .ACCEPTED ,com.intelli5.labourlink.entity.BookingStage .PENDING,com.intelli5.labourlink.entity.BookingStage .DECLINED )" +
//            "group by b.jobRole "
//    )
//    List<BookingCountDTO> getBookingCountWithJobRole();
//    //------------------Booking count for each jobroles : pending------------------------
//    @Query(
//            "select  (b.jobRole ,COUNT(DISTINCT b.id)) " +
//                    "from Booking b " +
//                    "where b.bookingStage IN (com.intelli5.labourlink.entity.BookingStage .PENDING )" +
//                    "group by b.jobRole "
//    )
//    List<BookingCountDTO> getPendingCountWithJob();
//    //------------------Booking count for each jobroles : declined------------------------
//    @Query(
//            "select  (b.jobRole ,COUNT(DISTINCT b.id)) " +
//                    "from Booking b " +
//                    "where b.bookingStage = com.intelli5.labourlink.entity.BookingStage .DECLINED " +
//                    "group by b.jobRole "
//    )
//    List<BookingCountDTO> getDeclinedCountWithJob();
//
//    //------------------Booking count for each jobroles : accept------------------------
//    @Query(
//            "select  (b.jobRole ,COUNT(DISTINCT b.id)) " +
//                    "from Booking b " +
//                    "where b.bookingStage = com.intelli5.labourlink.entity.BookingStage .ACCEPTED " +
//                    "group by b.jobRole "
//    )
//    List<BookingCountDTO> getAcceptCountWithJob();
//
//    //------------------Booking count for each jobroles : complete------------------------
//    @Query(
//            "select  (b.jobRole ,COUNT(DISTINCT b.id)) " +
//                    "from Booking b " +
//                    "where b.bookingStage = com.intelli5.labourlink.entity.BookingStage .COMPLETED " +
//                    "group by b.jobRole "
//    )
//    List<BookingCountDTO> getCompleteCountWithJob();
    //------------------Booking : High demand job role + ------------------------
    @Query(
            "select  (b.jobRole) " +
                    "from Booking b " +
                    "where b.bookingStage IN (com.intelli5.labourlink.entity.BookingStage .COMPLETED , com.intelli5.labourlink.entity.BookingStage .ACCEPTED ,com.intelli5.labourlink.entity.BookingStage .PENDING,com.intelli5.labourlink.entity.BookingStage .DECLINED )" +
                    "group by b.jobRole " +
                    "order by COUNT(b.id)desc " +
                    "limit 1"
    )
    JobRole getDemandedJob();
    //------------------Job : booking vs jobrole------------------------

    @Query(" SELECT b.jobRole AS jobRole, COUNT(b.id) AS count" +
            " FROM Booking b " +
            "GROUP BY jobRole "
    )
    List<Object[]> countBookingByJobRole();

}
