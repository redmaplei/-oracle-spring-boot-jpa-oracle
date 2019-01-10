package com.oracle.car_rental.controller;

import cn.hutool.core.util.ObjectUtil;
import com.oracle.car_rental.entity.Car;
import com.oracle.car_rental.entity.Leaser;
import com.oracle.car_rental.entity.RentCred;
import com.oracle.car_rental.entity.RepayCred;
import com.oracle.car_rental.service.CarService;
import com.oracle.car_rental.utils.ResultUtil;
import com.oracle.car_rental.vo.CarVO;
import com.oracle.car_rental.vo.LeaserCarVO;
import com.oracle.car_rental.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主要功能
 *
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

        return ResultUtil.success(carVOS.isEmpty() ? "暂无数据" : carVOS);
    }

    /**
     * 出租人可查看自己车辆的出租信息
     * （但不能获知租车人的信息）
     *
     */
    @PostMapping("/leaserget")
    public ResultVO leaserGetCar(@RequestParam String idNumber) {

        List<LeaserCarVO> carVOS = carService.leaserGetCar(idNumber);

        return ResultUtil.success(carVOS.isEmpty() ? "暂无数据" : carVOS);
    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    @GetMapping("/rent")
    public ResultVO rentCar(@RequestParam String companySalesmanName,
                            @RequestParam String idNumber,
                            @RequestParam String carNumber,
                            @RequestParam Integer rentDay,
                            @RequestParam Integer deposit) {

        RentCred rentCred = carService.rentCar(companySalesmanName, idNumber, carNumber, rentDay, deposit);
        if (ObjectUtil.isNotNull(rentCred)) {
            return ResultUtil.success(1,"租车成功 租车凭证", rentCred);
        }
        return ResultUtil.success("租车失败");
    }

    /**
     * 公司业务员还车业务
     */
    @PostMapping("/repay")
    public ResultVO repayCar(@RequestParam String companySalesmanName,
                             @RequestParam String idNumber,
                             @RequestParam String carNumber) {
        RepayCred repayCred = carService.repayCar(companySalesmanName, idNumber, carNumber);
        if (ObjectUtil.isNotNull(repayCred)) {
            return ResultUtil.success(1,"还车成功 还车凭证", repayCred);
        }
        return ResultUtil.success("还车失败");
    }

    /**
     * 管理员可维护车辆的基本信息
     */
    @PostMapping("/updatecar")
    public ResultVO adminUpdateCar(@RequestParam Long aid, @RequestBody Car car) {

        Boolean result = carService.adminUpdateCar(aid, car);

        return ResultUtil.success( result ? "车辆的基本信息更新成功" : "车辆的基本信息更新失败" );
    }

    /**
     * 管理员可维护出租人的基本信息
     */
    @PostMapping("/updatelea")
    public ResultVO adminUpdateLeaser(@RequestParam Long aid, @RequestBody Leaser leaser) {

        Boolean result = carService.adminUpdateLeaser(aid, leaser);

        return ResultUtil.success( result ? "出租人的基本信息更新成功" : "出租人的基本信息更新失败" );
    }

    /**
     * 查看每辆车某个时间段的出租天数、盈利状况
     */
    @PostMapping("/getdp")
    public ResultVO adminGetDayAndprofit() {

        return ResultUtil.success();
    }
}
