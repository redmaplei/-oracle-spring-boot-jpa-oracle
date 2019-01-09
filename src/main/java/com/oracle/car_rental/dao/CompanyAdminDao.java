package com.oracle.car_rental.dao;

import com.oracle.car_rental.entity.CompanyAdmin;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author wys
 * created in 23:26 2019/1/7
 */
@Repository
public interface CompanyAdminDao extends BaseMapper<CompanyAdmin> {
}
