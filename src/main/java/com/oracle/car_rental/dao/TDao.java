package com.oracle.car_rental.dao;

import com.oracle.car_rental.entity.T;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author wys
 * created in 19:45 2019/1/9
 */
@Repository
public interface TDao extends BaseMapper<T> {
}
