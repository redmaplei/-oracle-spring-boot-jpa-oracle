package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 一次出租信息
 *
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "rentalinfo")
public class RentalInfo {

    @Id
    private Long id;

    //    @Column(columnDefinition = "integer(255) COMMENT '车辆的号牌'")
    private String carNumber;

//    @NotNull
//    @Column(columnDefinition = "integer(255) COMMENT '身份证号码'")
    private String idNumber;

//    @Column(columnDefinition = "varchar(255) COMMENT '姓名'")
    private String name;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

//    @Column(columnDefinition = "datetime COMMENT '租车时间'")
    private Date rentTime;

//    @Column(columnDefinition = "datetime COMMENT '预期还车时间'")
    private Date preDeadLine;

//    @Column(columnDefinition = "datetime COMMENT '还车时间'")
    private Date rentDeadLine;

//    @Column(columnDefinition = "integer(255) COMMENT '押金'")
    private Integer deposit;

//    @Column(columnDefinition = "varchar(255) COMMENT '车辆状况描述'")
    private String carStatusDescribe;

//    @Column(columnDefinition = "varchar(255) COMMENT '出租经手人编号'")
    private String rentAgentNumber;

//    @Column(columnDefinition = "varchar(255) COMMENT '还车经手人编号'")
    private String repayAgentNumber;

}
