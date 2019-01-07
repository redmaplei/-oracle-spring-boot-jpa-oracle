package com.oracle.car_rental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 账号表
 *
 * @author wys
 * created in 22:52 2019/1/7
 */
@Data
@Entity
public class Account {
    @Id
    private Long aid;

    @Column(columnDefinition = "varchar(255) COMMENT '角色'")
    private String role;

}
