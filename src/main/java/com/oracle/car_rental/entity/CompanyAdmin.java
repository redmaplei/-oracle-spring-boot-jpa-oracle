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
import javax.validation.constraints.NotNull;

/**
 * 管理员
 *
 * @author wys
 * created in 22:32 2019/1/7
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companyadmin")
public class CompanyAdmin {
    @AutoID
    private Long id;

//    @Column(columnDefinition = "integer(255) COMMENT '账号id'")
    private Long aid;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '管理员'")
    private String salesmanName;

}
