package com.oracle.car_rental.dao;

import com.oracle.car_rental.entity.Car;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author wys
 * created in 23:24 2019/1/7
 */
@Repository
public interface CarDao extends BaseMapper<Car> {
}
