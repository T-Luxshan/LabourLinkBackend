/*package com.intelli5.labourlink.impl;

import com.intelli5.labourlink.service.LabourJobService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LabourJobImpl implements LabourJobService {
    @Override
    public List<Object[]> getLabourJobCountsWithId() {
        @Autowired
        private EntityManager entityManager;

        @Override
        public List<Object[]> getLabourJobCountsWithId() {
            String nativeQuery =  "SELECT COUNT(l.labour_Id), j.jobName " +
                    "FROM Labour_Job l JOIN Job j ON l.jobId = j.jobId " +
                    "GROUP BY l.jobId";
            Query query = entityManager.createNativeQuery(nativeQuery);
            return query.getResultList();
    }
}
*/