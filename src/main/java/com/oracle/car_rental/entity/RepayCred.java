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
import java.util.Date;

/**
 * 还车凭证
 *
 * @author wys
 * created in 23:07 2019/1/7
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repaycred")
public class RepayCred {
    @AutoID
    private Long id;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

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

//    @Column(columnDefinition = "datetime COMMENT '还车时间'")
    private Date rentDeadLine;

//    @Column(columnDefinition = "integer(255) COMMENT '租金'")
    private Integer theRent;

//    @Column(columnDefinition = "varchar(255) COMMENT '补交金额'")
    private String payAmount;

    /**
     * 当逾期还车押金不够时
     */
//    @Column(columnDefinition = "integer(255) COMMENT '找零'")
    private Integer change;

    /**
     * 押金退还（押金-租金）
     */
//    @Column(columnDefinition = "integer(255) COMMENT '押金退还'")
    private Integer depositRefund;


}
