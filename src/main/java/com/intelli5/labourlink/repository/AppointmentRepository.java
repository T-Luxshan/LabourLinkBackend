package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
    List<Appointment> findByCustomer(Customer customer);
    List<Appointment> findByLabour(Labour labour);

    @Query("select SUM(a.taskRevenue) from Appointment a")
    Double sumTaskRevenue();

//Appointment graph :1 ----------------------------
    @Query("select j.jobName, COUNT(a.job.jobId) " +
            "from Appointment a " +
            "join Job j on j.jobId = a.job.jobId " +
            "group by a.job.jobId")
    List<Object[]> findJobAppointmentCounts();

    //Appointment graph :2 ----------------------------
    @Query("select j.jobName, COUNT(a.job.jobId) " +
            "from Appointment a " +
            "join Job j on j.jobId = a.job.jobId " +
            " where a.isCancelled = true " +
            "group by a.job.jobId")
    List<Object[]> findCancelledJobAppointmentCounts();

    //Dashboard graph :1 ----------------------------
        @Query(
            "select dayname(a.AppointmentMadeDate) AS day_of_week, count(a.id) AS total_appointments " +
                    "from Appointment a " +
                    "where a.AppointmentMadeDate > :startDate " +
                    "group by dayname(a.AppointmentMadeDate), a.AppointmentMadeDate " +
                    "order by field(dayname(a.AppointmentMadeDate), 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday' ,'Friday', 'Saturday')"
    )
    List<Object[]> findActiveCustomerCount(@Param("startDate") LocalDate startDate);

    //Dashboard graph :2 ----------------------------
    @Query(
            "select dayname(a.appointmentFixedDate) AS day_of_week, count(a.id) AS total_appointments " +
                    "from Appointment a " +
                    "where a.appointmentFixedDate > :startDate  and a.isDelivered= true "  +
                    "group by dayname(a.appointmentFixedDate), a.appointmentFixedDate " +
                    "order by field(dayname(a.appointmentFixedDate), 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday' ,'Friday', 'Saturday')"
    )
    List<Object[]> findActiveLabourCount(@Param("startDate") LocalDate startDate);

    //Dashboard graph :3 ----------------------------
    @Query(
            "select dayname(a.AppointmentMadeDate) AS day_of_week, count(a.id) AS total_appointments " +
                    "from Appointment a " +
                    "where a.AppointmentMadeDate > :startDate  and a.isCancelled= false "  +
                    "group by dayname(a.AppointmentMadeDate), a.AppointmentMadeDate " +
                    "order by field(dayname(a.AppointmentMadeDate), 'Sun', 'Mon', 'Tues', 'Wed', 'Thur' ,'Fri', 'Sat')"
    )
    List<Object[]> findAppointmentsCountWithDay(@Param("startDate") LocalDate startDate);



}