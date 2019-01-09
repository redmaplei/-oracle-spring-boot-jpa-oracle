package com.oracle.car_rental.utils;

import cn.hutool.core.util.ObjectUtil;
import com.oracle.car_rental.entity.Car;
import com.oracle.car_rental.vo.CarVO;

/**
 * @author wys
 * created in 15:10 2019/1/9
 */
public class CarVoUtil {

    public CarVoUtil() {

    }

    public static CarVO toCarVo(Car car) {
        CarVO.CarVOBuilder carVOBuilder = CarVO.builder();
        if (ObjectUtil.isNull(car)) {
            return null;
        }
        return carVOBuilder
                .carNumber(car.getCarNumber())
                .brand(car.getBrand())
                .describe(car.getDescribe())
                .buyTime(car.getBuyTime())
                .dailyRent(car.getDailyRent())
                .rentStartTime(car.getRentStartTime())
                .rentDeadLine(car.getRentDeadLine())
                .profit(car.getProfit())
                .isNormal(car.getIsNormal())
                .build();
    }

}
