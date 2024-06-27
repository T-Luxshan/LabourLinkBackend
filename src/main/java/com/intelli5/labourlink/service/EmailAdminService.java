package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.repository.EmailAdminRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailAdminService {

    EmailAdminRepository emailAdminRepository;
    public List<EmailAdmin> findAll() {
        return emailAdminRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
