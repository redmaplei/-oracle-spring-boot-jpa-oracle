package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.RentCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentCredRepository extends JpaRepository<RentCred ,Long> {
}
