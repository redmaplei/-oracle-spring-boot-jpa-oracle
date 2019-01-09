package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 租车凭证
 *
 * @author wys
 * created in 23:07 2019/1/7
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentcred")
public class RentCred {
    @AutoID
    private Long id;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

//    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

//    @Column(columnDefinition = "integer(255) COMMENT '凭证号'")
    private Integer credNumber;

//    @Column(columnDefinition = "integer(255) COMMENT '车辆的号牌'")
    private String carNumber;

//    @Column(columnDefinition = "varchar(255) COMMENT '品牌'")
    private String brand;

//    @Column(columnDefinition = "integer(255) COMMENT '日租金'")
    private Integer dailyRent;

//    @Column(columnDefinition = "integer(255) COMMENT '所交押金'")
    private Integer deposit;

//    @Column(columnDefinition = "datetime COMMENT '预期还车时间'")
    private Date preDeadLine;

    /**
     * 就是业务员的编号
     */
//    @Column(columnDefinition = "varchar(255) COMMENT '出租经手人编号'")
    private String rentAgentNumber;

}
