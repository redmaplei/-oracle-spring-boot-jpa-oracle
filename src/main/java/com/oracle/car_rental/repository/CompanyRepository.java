package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company ,Long> {

    List<Company> findAllByCompanyName(String name);

}
