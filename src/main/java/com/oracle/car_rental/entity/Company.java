package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
//import org.beetl.sql.core.annotatoin.AutoID;
//import org.beetl.sql.core.annotatoin.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;

/**
 * 租赁公司
 *
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "company")
public class Company {

    @Id
    private Long id;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

//    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

}
