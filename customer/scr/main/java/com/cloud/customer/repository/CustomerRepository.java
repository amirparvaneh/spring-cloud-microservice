package com.cloud.customer.repository;

import com.cloud.customer.model.Customer;
import com.cloud.customer.model.CustomerStatus;
import com.cloud.customer.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByNationalCode(String nationalCode);

    void deleteCustomerByNationalCode(String nationalCode);

    List<Customer> findCustomerByFirstNameOrLastNameOrCustomerStatusOrCustomerType(String firstName,
                                                                                   String lastName,
                                                                                   CustomerStatus customerStatus,
                                                                                   CustomerType customerType);
}
