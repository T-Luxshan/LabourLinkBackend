package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationReportDTO {
    private Integer id;
    private String title;
    private String ReportedByName;
    private String ReportedToId;
//    private LocalDateTime reportedOn;
}
