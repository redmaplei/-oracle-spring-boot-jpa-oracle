package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customers ,Long> {

    List<Customers> findAllByIdNumber(String idNumber);

    Customers findByIdNumber(String idNumber);

}
