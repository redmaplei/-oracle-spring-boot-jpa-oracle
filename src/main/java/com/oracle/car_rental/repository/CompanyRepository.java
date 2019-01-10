package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company ,Long> {

    Company findByCompanyName(String name);

}
