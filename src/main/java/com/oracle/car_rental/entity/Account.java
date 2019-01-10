package com.oracle.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 账号表
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "account")
public class Account {
    /**
     * 账号
     */
    @Id
    private Long aid;

//    @Column(columnDefinition = "varchar(255) COMMENT '角色'")
    private String role;

}
