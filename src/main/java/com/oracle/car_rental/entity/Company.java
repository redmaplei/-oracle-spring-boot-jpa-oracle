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

/**
 * 租赁公司
 *
 * @author wys
 * created in 22:28 2019/1/7
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {

    @AutoID
    private Long id;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

//    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

}
