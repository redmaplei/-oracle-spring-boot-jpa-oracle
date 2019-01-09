package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;


/**
 * 账号表
 *
 * @author wys
 * created in 22:52 2019/1/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    /**
     * 账号
     */
    @AutoID
    private Long aid;

//    @Column(columnDefinition = "varchar(255) COMMENT '角色'")
    private String role;

}
