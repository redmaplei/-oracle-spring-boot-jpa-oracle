package com.oracle.car_rental.controller;

import cn.hutool.core.util.ObjectUtil;
import com.oracle.car_rental.entity.*;
import com.oracle.car_rental.exception.FrameErrorCodeEnums;
import com.oracle.car_rental.exception.FrameRuntimeException;
import com.oracle.car_rental.repository.*;
import com.oracle.car_rental.service.CarService;
import com.oracle.car_rental.utils.ResultUtil;
import com.oracle.car_rental.vo.CarVO;
import com.oracle.car_rental.vo.LeaserCarVO;
import com.oracle.car_rental.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ResultVO leaserGetCar(@RequestParam Long aid, @RequestParam String idNumber) {

        List<LeaserCarVO> carVOS = carService.leaserGetCar(aid, idNumber);

        return ResultUtil.success(carVOS.isEmpty() ? "暂无数据" : carVOS);
    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    @PostMapping("/rent")
    public ResultVO rentCar(@RequestParam Long salesmanAid,
                            @RequestParam String idNumber,
                            @RequestParam String carNumber,
                            @RequestParam Integer rentDay,
                            @RequestParam Integer deposit) {

        RentCred rentCred = carService.rentCar(salesmanAid, idNumber, carNumber, rentDay, deposit);
        if (ObjectUtil.isNotNull(rentCred)) {
            return ResultUtil.success(1,"租车成功 租车凭证", rentCred);
        }
        return ResultUtil.success("租车失败");
    }

    /**
     * 公司业务员还车业务
     */
    @PostMapping("/repay")
    public ResultVO repayCar(@RequestParam Long salesmanAid,
                             @RequestParam String idNumber,
                             @RequestParam String carNumber) {
        RepayCred repayCred = carService.repayCar(salesmanAid, idNumber, carNumber);
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

    @Autowired
    private CustomersRepository customersRepository;

    // 出租人 的增删改查
    /**
     * 增加出租人
     * @param customers
     * @return
     */
    @PostMapping("/addcus")
    public ResultVO addCus(@RequestBody Customers customers) {
        Optional<Customers> byId = customersRepository.findById(customers.getId());
        if (byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_A_ACCOUNT);
        }
        customersRepository.save(customers);
        List<Customers> all1 = customersRepository.findAll();
        customers.setId(all1.get(all1.size() - 1).getId() + 1);

        customersRepository.save(customers);


        return ResultUtil.success("增加成功");
    }


    /**
     * 删除出租人
     * @return
     */
    @GetMapping("/delete")
    public ResultVO deleteCus(@RequestParam Long id) {
        Optional<Customers> byId = customersRepository.findById(id);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        customersRepository.deleteById(id);

        return ResultUtil.success("删除成功");

    }

    /**
     * 更新出租人
     * @param customers
     * @return
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody Customers customers) {
        Optional<Customers> byId = customersRepository.findById(customers.getId());
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }
        customersRepository.save(customers);


        return ResultUtil.success("更新成功");
    }

    /**
     * 获得一个出租人
     */
    @GetMapping("/getcus")
    public ResultVO getallcus(Long id) {
        Optional<Customers> byId = customersRepository.findById(id);
        log.info("{}", byId);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        Optional<Customers> byId1 = customersRepository.findById(id);

        return ResultUtil.success( byId1.isPresent() ? byId : "没有这个出租人");

    }


    // 汽车 的增删改查

    @Autowired
    private CarRepository carRepository;
    /**
     * 增加汽车
     * @param car
     * @return
     */
    @PostMapping("/addCar")
    public ResultVO addCar(@RequestBody Car car) {
        Optional<Car> byId = carRepository.findById(car.getId());
        if (byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_A_ACCOUNT);
        }
        carRepository.save(car);
        List<Customers> all1 = customersRepository.findAll();
        car.setId(all1.get(all1.size() - 1).getId() + 1);

        carRepository.save(car);


        return ResultUtil.success("增加成功");
    }


    /**
     * 删除汽车
     * @return
     */
    @GetMapping("/deleteCar")
    public ResultVO deleteCar(@RequestParam Long id) {
        Optional<Customers> byId = customersRepository.findById(id);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        customersRepository.deleteById(id);

        return ResultUtil.success("删除成功");

    }

    /**
     * 更新汽车
     * @param car
     * @return
     */
    @PostMapping("/updateCar")
    public ResultVO updateCar(@RequestBody Car car) {
        Optional<Car> byId = carRepository.findById(car.getId());
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }
        carRepository.save(car);


        return ResultUtil.success("更新成功");
    }

    /**
     * 获得一个汽车
     */
    @GetMapping("/getCar")
    public ResultVO getallCar(Long id) {
        Optional<Car> byId = carRepository.findById(id);

        log.info("{}", byId);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        Optional<Car> byId1 = carRepository.findById(id);

        return ResultUtil.success( byId1.isPresent() ? byId : "没有这个汽车");

    }


    // 出租人 的增删改查

    @Autowired
    private LeaserRepository leaserRepository;
    /**
     * 增加出租人
     * @param leaser
     * @return
     */
    @PostMapping("/addLeaser")
    public ResultVO addLeaser(@RequestBody Leaser leaser) {
        Optional<Leaser> byId = leaserRepository.findById(leaser.getId());
        if (byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_A_ACCOUNT);
        }
        leaserRepository.save(leaser);
        List<Leaser> all1 = leaserRepository.findAll();
        leaser.setId(all1.get(all1.size() - 1).getId() + 1);

        leaserRepository.save(leaser);


        return ResultUtil.success("增加成功");
    }


    /**
     * 删除出租人
     * @return
     */
    @GetMapping("/deleteLeaser")
    public ResultVO deleteLeaser(@RequestParam Long id) {
        Optional<Leaser> byId = leaserRepository.findById(id);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        leaserRepository.deleteById(id);

        return ResultUtil.success("删除成功");

    }

    /**
     * 更新出租人
     * @param leaser
     * @return
     */
    @PostMapping("/updateLeaser")
    public ResultVO updateLeaser(@RequestBody Leaser leaser) {
        Optional<Leaser> byId = leaserRepository.findById(leaser.getId());
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }
        leaserRepository.save(leaser);


        return ResultUtil.success("更新成功");
    }

    /**
     * 获得一个出租人
     */
    @GetMapping("/getLeaser")
    public ResultVO getallLeaser(Long id) {
        Optional<Leaser> byId = leaserRepository.findById(id);
        log.info("{}", byId);
        if (!byId.isPresent()) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }

        Optional<Leaser> byId1 = leaserRepository.findById(id);

        return ResultUtil.success( byId1.isPresent() ? byId : "没有这个出租人");

    }

}
