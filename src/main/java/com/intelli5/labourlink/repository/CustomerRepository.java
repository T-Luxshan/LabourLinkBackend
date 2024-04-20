package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("customerRepository")
public interface CustomerRepository extends UserRepository {

}
