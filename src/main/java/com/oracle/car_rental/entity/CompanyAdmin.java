package com.oracle.car_rental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 管理员
 *
 * @author wys
 * created in 22:32 2019/1/7
 */
@Data
@Entity
public class CompanyAdmin {
    @Id
    private Long id;

    @Column(columnDefinition = "integer(255) COMMENT '账号id'")
    private Long aid;

    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

    @NotNull
    @Column(columnDefinition = "varchar(255) COMMENT '管理员'")
    private String salesmanName;

}
