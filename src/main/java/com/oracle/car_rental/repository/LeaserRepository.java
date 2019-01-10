package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.Leaser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaserRepository extends JpaRepository<Leaser ,Long> {
}
