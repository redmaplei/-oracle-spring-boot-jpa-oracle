package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 出租人
 *
 */
@Data
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "leaser")
public class Leaser {

    @Id
    private Long id;

//    @Column(columnDefinition = "integer(255) COMMENT '账号id'")
    private Long aid;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '身份证号码'")
    private String idNumber;

//    @Column(columnDefinition = "varchar(255) COMMENT '姓名'")
    private String name;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String phoneNumber;

//    @NotNull
//    @Column(columnDefinition = "varchar(255) COMMENT '性别'")
    private String gender;

}
