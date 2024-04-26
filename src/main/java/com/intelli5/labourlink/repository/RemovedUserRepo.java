package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.RemovedUserDetail;
import com.intelli5.labourlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemovedUserRepo extends JpaRepository<RemovedUserDetail,String> {
}
