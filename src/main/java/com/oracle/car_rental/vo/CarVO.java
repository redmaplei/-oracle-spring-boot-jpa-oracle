package com.oracle.car_rental.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wys
 * created in 16:34 2019/1/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {

    private Integer carNumber;

    private String brand;

    private String describe;

    private Date buyTime;

    private Integer dailyRent;

    private Date rentStartTime;

    private Date rentDeadLine;

    private Integer profit;

    /**
     * true正常  false维修
     */
    private String isNormal;
}
