package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.RepayCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepayCredRepository extends JpaRepository<RepayCred ,Long> {
}
