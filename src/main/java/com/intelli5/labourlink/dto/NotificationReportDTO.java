package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.User;
import lombok.Data;

@Data
public class NotificationReportDTO {
    private String title;
    private String ReportedByName;
    private String ReportedToId;
}
