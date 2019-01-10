package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 车辆
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "car")
public class Car {

    @Id
    private Long id;

//    @Column(columnDefinition = "integer(255) COMMENT '车辆的号牌'")
    private String carNumber;

//    @Column(columnDefinition = "varchar(255) COMMENT '品牌'")
    private String brand;

//    @Column(columnDefinition = "varchar(255) COMMENT '描述信息'")
    private String describe;

    /**
     * 出租人身份证号码知道是哪个出租人的车
     */
//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '出租人身份证号码'")
    private String idNumber;

//    @Column(columnDefinition = "datetime COMMENT '初次购买日期'")
    private String buyTime;

//    @Column(columnDefinition = "integer(255) COMMENT '日租金'")
    private Integer dailyRent;

//    @Column(columnDefinition = "datetime COMMENT '出租起始时间'")
    private String rentStartTime;

//    @Column(columnDefinition = "datetime COMMENT '出租截止时间'")
    private String rentDeadLine;

//    @Column(columnDefinition = "integer(255) COMMENT '盈利'")
    private Integer profit;

    /**
     * true正常  false维修
     */
//    @Column(columnDefinition = "bit(1) COMMENT '是否是正常'")
    private String isNormal;

    /**
     *是不是在出租中 空闲 出租中
     *
     */
    private String isRent;
}
