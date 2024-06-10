package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("customerRepository")
public interface CustomerRepository extends UserRepository {

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findCustomer(String email);
}
