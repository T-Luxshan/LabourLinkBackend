package com.intelli5.labourlink.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("labourRepository")
public interface LabourRepository extends UserRepository{

}
