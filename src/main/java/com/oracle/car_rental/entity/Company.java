package com.oracle.car_rental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 租赁公司
 *
 * @author wys
 * created in 22:28 2019/1/7
 */
@Data
@Entity
public class Company {

    @Id
    private Long id;

    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

    @NotNull
    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

}
