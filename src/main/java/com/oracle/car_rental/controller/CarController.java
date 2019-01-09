package com.oracle.car_rental.controller;

import com.oracle.car_rental.service.CarService;
import com.oracle.car_rental.utils.ResultUtil;
import com.oracle.car_rental.vo.CarVO;
import com.oracle.car_rental.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主要功能
 *
 * @author wys
 * created in 23:31 2019/1/7
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    /**
     * 任何人可查看车辆的基本信息
     */
    @GetMapping("/getcar")
    public ResultVO getCar() {

        List<CarVO> carVOS = carService.getCar();

        return ResultUtil.success( carVOS.isEmpty() ? carVOS : "暂无数据");
    }

    /**
     * 出租人可查看自己车辆的出租信息
     * （但不能获知租车人的信息）
     *
     */
    @PostMapping("/leaserget")
    public ResultVO leaserGetCar() {

        return ResultUtil.success();
    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    @PostMapping("/rent")
    public ResultVO rentCar() {

        return ResultUtil.success();
    }

    /**
     * 公司业务员还车业务
     */
    @PostMapping("/repay")
    public ResultVO repayCar() {

        return ResultUtil.success();
    }

    /**
     * 管理员可维护车辆的基本信息
     */
    @PostMapping("/updatecar")
    public ResultVO adminUpdateCar() {

        return ResultUtil.success();
    }

    /**
     * 管理员可维护出租人的基本信息
     */
    @PostMapping("/updatelea")
    public ResultVO adminUpdateLeaser() {

        return ResultUtil.success();
    }

    /**
     * 查看每辆车某个时间段的出租天数、盈利状况
     */
    @PostMapping("/getdp")
    public ResultVO adminGetDayAndprofit() {

        return ResultUtil.success();
    }
}
