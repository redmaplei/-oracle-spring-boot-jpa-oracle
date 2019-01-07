package com.oracle.car_rental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 一次出租信息
 *
 * @author wys
 * created in 21:43 2019/1/7
 */
@Data
@Entity
public class RentalInfo {

    @Id
    private Long id;

    @NotNull
    @Column(columnDefinition = "integer(255) COMMENT '身份证号码'")
    private Integer idNumber;

    @Column(columnDefinition = "varchar(255) COMMENT '姓名'")
    private String name;

    @NotNull
    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

    @Column(columnDefinition = "datetime COMMENT '租车时间'")
    private Date rentalTime;

    @Column(columnDefinition = "datetime COMMENT '预期还车时间'")
    private Date preDeadLine;

    @Column(columnDefinition = "datetime COMMENT '还车时间'")
    private Date rentalDeadLine;

    @Column(columnDefinition = "varchar(255) COMMENT '押金'")
    private String deposit;

    @Column(columnDefinition = "varchar(255) COMMENT '车辆状况描述'")
    private String carStatusDescribe;

    @Column(columnDefinition = "varchar(255) COMMENT '出租经手人编号'")
    private String rentalAgentNumber;

    @Column(columnDefinition = "varchar(255) COMMENT '还车经手人编号'")
    private String returnAgentNumber;

}
