package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 公司业务员
 *
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "companysalesman")
public class CompanySalesman {
    @Id
    private Long id;

//    @Column(columnDefinition = "integer(255) COMMENT '账号id'")
    private Long aid;

//    @Column(columnDefinition = "varchar(255) COMMENT '租赁公司名'")
    private String companyName;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '公司业务员'")
    private String salesmanName;


}
