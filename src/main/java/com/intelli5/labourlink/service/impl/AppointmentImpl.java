package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.dto.AppointmentDTO;
import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.repository.AppointmentRepo;
import com.intelli5.labourlink.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentImpl implements AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
  /*  @Override
    public List<Appointment> findAll() {
        Sort sort = Sort.by(Sort.Order.desc("AppointmentMadeDate"));
        return appointmentRepo.findAll();
    }*/

    @Override
    public List<AppointmentDTO> getPendingAppointmentsWithDetails() {
        List<Appointment> appointments = appointmentRepo.findAll();
        List<AppointmentDTO> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if(!appointment.isDelivered() && !appointment.isCancelled()){
                AppointmentDTO appointmentDto = new AppointmentDTO();
                appointmentDto.setId(appointment.getId());
                appointmentDto.setCustomerName(appointment.getCustomer().getName());
                appointmentDto.setCustomerEmail(appointment.getCustomer().getEmail());
                appointmentDto.setLabourName(appointment.getLabour().getName());
                appointmentDto.setJobTitle(appointment.getJob().getJobName());
                appointmentDto.setAppointmentFixedDate(appointment.getAppointmentFixedDate());

                appointmentDtos.add(appointmentDto);
            }}
        return appointmentDtos;
    }

    public List<AppointmentDTO> getDeliveredAppointmentsWithDetails() {
        List<Appointment> appointments = appointmentRepo.findAll(); // Fetch appointments from repository
        List<AppointmentDTO> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments) {
if(appointment.isDelivered()){
            AppointmentDTO appointmentDto = new AppointmentDTO();
            appointmentDto.setId(appointment.getId());
            appointmentDto.setCustomerName(appointment.getCustomer().getName());
            appointmentDto.setCustomerEmail(appointment.getCustomer().getEmail());
            appointmentDto.setLabourName(appointment.getLabour().getName());
            appointmentDto.setJobTitle(appointment.getJob().getJobName());
            appointmentDto.setAppointmentFixedDate(appointment.getAppointmentFixedDate());

            appointmentDtos.add(appointmentDto);
        }}
        return appointmentDtos;
    }

    public List<AppointmentDTO> getCancelAppointmentsWithDetails() {
        List<Appointment> appointments = appointmentRepo.findAll(); // Fetch appointments from repository
        List<AppointmentDTO> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if(appointment.isCancelled()){
                AppointmentDTO appointmentDto = new AppointmentDTO();
                appointmentDto.setId(appointment.getId());
                appointmentDto.setCustomerName(appointment.getCustomer().getName());
                appointmentDto.setCustomerEmail(appointment.getCustomer().getEmail());
                appointmentDto.setLabourName(appointment.getLabour().getName());
                appointmentDto.setJobTitle(appointment.getJob().getJobName());
                appointmentDto.setAppointmentFixedDate(appointment.getAppointmentFixedDate());

                appointmentDtos.add(appointmentDto);
            }}
        return appointmentDtos;
    }
    public  double sumByRevenue() {

        return  appointmentRepo.sumTaskRevenue();
    }
public List<Object[]> jobVsTotalAppointment(){
   return appointmentRepo.findJobAppointmentCounts();
}
    public List<Object[]> findCancelledJobAppointmentCounts(){
        return appointmentRepo.findCancelledJobAppointmentCounts();
    }
    public List<Object[]> findActiveCustomerCount(){
        LocalDate startDate = LocalDate.now().minusDays(7);
        return appointmentRepo.findActiveCustomerCount(startDate);
    }

    public List<Object[]> findActiveLabourCount(){
        LocalDate startDate = LocalDate.now().minusDays(7);
        return appointmentRepo.findActiveLabourCount(startDate);
    }

    public List<Object[]> findAppointmentsCountWithDay(){
        LocalDate startDate = LocalDate.now().minusDays(7);
        return appointmentRepo.findAppointmentsCountWithDay(startDate);
    }
}
