package com.oracle.car_rental.dao;

import com.oracle.car_rental.entity.Account;
import org.beetl.sql.core.mapper.BaseMapper;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wys
 * created in 23:25 2019/1/7
 */
@Repository
public interface AccountDao extends BaseMapper<Account> {
}
