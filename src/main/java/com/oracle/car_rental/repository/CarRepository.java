package com.oracle.car_rental.repository;

import com.oracle.car_rental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car ,Long> {

    /**
     * 通过出租人的身份证查看自己车辆的出租信息
     * @param idNumber 身份证号码
     * @return
     */
    List<Car> findAllByIdNumber(String idNumber);

    /**
     * 通过汽车的车牌获得汽车的信息
     * @param carNumber
     * @return
     */
    Car findByCarNumber(String carNumber);

}
