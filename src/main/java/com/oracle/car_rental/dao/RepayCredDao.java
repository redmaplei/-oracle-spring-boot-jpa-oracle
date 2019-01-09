package com.oracle.car_rental.dao;

import com.oracle.car_rental.entity.RepayCred;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author wys
 * created in 23:28 2019/1/7
 */
@Repository
public interface RepayCredDao extends BaseMapper<RepayCred> {
}