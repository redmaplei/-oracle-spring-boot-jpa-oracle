package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.RentalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalInfoRepository extends JpaRepository<RentalInfo ,Long> {

    RentalInfo findByIdNumberAndCarNumberAndRepayAgentNumber(String idNumber, String carNumber, String d);

}
