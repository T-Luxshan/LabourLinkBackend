package com.intelli5.labourlink.utils;


import com.intelli5.labourlink.entity.JobRole;

import java.util.Arrays;
import java.util.List;

public class JobRoleEnumUtil {
    public static int coutJobRole() {
        return JobRole.values().length;
    }

    public static List<JobRole> getJobRoles() {
        return Arrays.asList(JobRole.values());
    }
}
