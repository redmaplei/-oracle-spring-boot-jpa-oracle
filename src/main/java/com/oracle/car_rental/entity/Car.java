package com.oracle.car_rental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 车辆
 *
 * @author wys
 * created in 21:35 2019/1/7
 */
@Data
@Entity
public class Car {

    @Id
    private Long id;

    @Column(columnDefinition = "integer(255) COMMENT '车辆的号牌'")
    private Integer carNumber;

    @Column(columnDefinition = "varchar(255) COMMENT '品牌'")
    private String brand;

    @Column(columnDefinition = "varchar(255) COMMENT '描述信息'")
    private String describe;

    @Column(columnDefinition = "datetime COMMENT '初次购买日期'")
    private Date buyTime;

    @Column(columnDefinition = "varchar(255) COMMENT '日租金'")
    private String dailyRental;

    @Column(columnDefinition = "datetime COMMENT '出租起始时间'")
    private Date rentalStartTime;

    @Column(columnDefinition = "datetime COMMENT '出租截止时间'")
    private Date rentalDeadLine;

    @Column(columnDefinition = "varchar(255) COMMENT '盈利'")
    private String profit;

    /**
     * true正常  false维修
     */
    @Column(columnDefinition = "bit(1) COMMENT '是否是正常'")
    private Boolean isNormal;

}