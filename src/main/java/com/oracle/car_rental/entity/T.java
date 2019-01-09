package com.oracle.car_rental.entity;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @author wys
 * created in 19:44 2019/1/9
 */
@Data
//@Table(name = "t")
public class T {

    @AutoID
    private Integer id;

}
