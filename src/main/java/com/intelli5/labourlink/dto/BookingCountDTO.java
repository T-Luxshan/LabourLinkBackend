package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingCountDTO {
    private JobRole jobRole;
    private int count;
}
