package com.intelli5.labourlink.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("labourRepository")

//Define the LabourRepository interface that extends UserRepository
public interface LabourRepository extends UserRepository{

}
