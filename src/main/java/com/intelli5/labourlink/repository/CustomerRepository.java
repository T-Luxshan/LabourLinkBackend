package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
