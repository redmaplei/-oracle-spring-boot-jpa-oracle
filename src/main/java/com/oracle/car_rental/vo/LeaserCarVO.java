package com.oracle.car_rental.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaserCarVO {

    private String carNumber;

    private Integer dailyRent;

    private Date rentStartTime;

    private Date rentDeadLine;

    private Integer profit;

    /**
     * true正常  false维修
     */
    private String isNormal;

    private String isRent;
}
