package com.oracle.car_rental.service;

import com.oracle.car_rental.dao.CarDao;
import com.oracle.car_rental.entity.Car;
import com.oracle.car_rental.utils.CarVoUtil;
import com.oracle.car_rental.vo.CarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * @author wys
 * created in 23:31 2019/1/7
 */
@Service
public class CarService {

    @Autowired
    private CarDao carDao;

    /**
     * 任何人可查看车辆的基本信息
     */
    public List<CarVO> getCar() {

        List<Car> carList = carDao.createLambdaQuery().select();

        List<CarVO> carVOS = carList.stream().map(CarVoUtil::toCarVo).collect(toList());

        return carVOS;
    }

    /**
     * 出租人可查看自己车辆的出租信息
     * （但不能获知租车人的信息）
     *
     */
    public void leaserGetCar() {

    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    public void rentCar() {

    }

    /**
     * 公司业务员还车业务
     */
    public void repayCar() {

    }

    /**
     * 管理员可维护车辆的基本信息
     */
    public void adminUpdateCar() {

    }

    /**
     * 管理员可维护出租人的基本信息
     */
    public void adminUpdateLeaser() {

    }

    /**
     * 查看每辆车某个时间段的出租天数、盈利状况
     */
    public void adminGetDayAndprofit() {

    }

}
