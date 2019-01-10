package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.CompanySalesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySalesmanRepository extends JpaRepository<CompanySalesman ,Long> {

    CompanySalesman findBySalesmanName(String salesmanName);
    CompanySalesman findByAid(Long aid);
}
