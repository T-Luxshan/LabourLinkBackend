package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusUpdateDTO {
    private BookingStage bookingStage;
}

