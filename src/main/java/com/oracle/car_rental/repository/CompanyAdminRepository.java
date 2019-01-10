package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin ,Long> {

    CompanyAdmin findByAid(Long aid);

}
