package com.oracle.car_rental.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.oracle.car_rental.entity.*;
import com.oracle.car_rental.exception.FrameErrorCodeEnums;
import com.oracle.car_rental.exception.FrameRuntimeException;
import com.oracle.car_rental.repository.*;
import com.oracle.car_rental.utils.CarVoUtil;
import com.oracle.car_rental.vo.CarVO;
import com.oracle.car_rental.vo.LeaserCarVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private RentalInfoRepository rentalInfoRepository;

    @Autowired
    private CompanySalesmanRepository companySalesmanRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RentCredRepository rentCredRepository;

    @Autowired
    private CompanyAdminRepository companyAdminRepository;

    @Autowired
    private LeaserRepository leaserRepository;

    /**
     * 任何人可查看车辆的基本信息
     */
    public List<CarVO> getCar() {

        List<Car> carList = carRepository.findAll();
        log.info("{}" , carList);
        List<CarVO> carVOS = carList.stream().map(CarVoUtil::toCarVo).collect(toList());

        return carVOS;
    }

    /**
     * 出租人可查看自己车辆的出租信息
     * （但不能获知租车人的信息）
     *
     */
    public List<LeaserCarVO> leaserGetCar(String idNumber) {

        List<Car> carList = carRepository.findAllByIdNumber(idNumber);

        List<LeaserCarVO> leaserCarVOS = carList.stream().map(CarVoUtil::toLeaserCarVo).collect(toList());

        return leaserCarVOS;
    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    public RentCred rentCar(String companySalesmanName, String idNumber, String carNumber, Integer rentDay, Integer deposit) {

        if (StrUtil.isBlank(companySalesmanName) || StrUtil.isBlank(idNumber) || StrUtil.isBlank(carNumber) ||
                ObjectUtil.isNull(rentDay) || ObjectUtil.isNull(deposit)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.BIND_ARGS_ERROR);
        }

        // 先判断对应的数据是否有
        Car car = carRepository.findByCarNumber(carNumber);
        Customers customers = customersRepository.findByIdNumber(idNumber);
        CompanySalesman companySalesman = companySalesmanRepository.findByCompanyName(companySalesmanName);
        Company company = companyRepository.findByCompanyName(companySalesman.getCompanyName());
        if (ObjectUtil.isNull(car) || ObjectUtil.isNull(customers)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.CARORCUSTOMER_EMPTY_ERROR);
        }
        if (car.getIsRent().equals("出租中")) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.CAR_EXIST);
        }
        if (ObjectUtil.isNull(companySalesman)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.COMPANYS_EXIST);
        }

        // 可以出租汽车
        RentCred rentCred = new RentCred();
        rentCred.setCarNumber(car.getCarNumber());
        rentCred.setBrand(car.getBrand());
        rentCred.setCompanyName(company.getCompanyName());
        rentCred.setPhoneNumber(company.getPhoneNumber());
        rentCred.setDailyRent(car.getDailyRent());
        rentCred.setDeposit(deposit);
        Date rentDate = new Date();
        Date repayDate = new Date();
        rentCred.setRentStartTime(rentDate);
//        rentCred.setPreDeadLine(date.);  ======
        rentCred.setRentAgentNumber(companySalesman.getAid().toString());

        RentCred save = rentCredRepository.save(rentCred);

        RentalInfo rentalInfo = new RentalInfo();
        rentalInfo.setCarNumber(car.getCarNumber());
        rentalInfo.setIdNumber(customers.getIdNumber());
        rentalInfo.setName(customers.getName());
        rentalInfo.setPhoneNumber(customers.getPhoneNumber());
        rentalInfo.setRentTime(rentDate);
        rentalInfo.setRentDeadLine(repayDate);
        rentalInfo.setRentAgentNumber(companySalesman.getAid().toString());
        RentalInfo saveRentalInfo = rentalInfoRepository.save(rentalInfo);

//        log.info("cuscnt {}", cuscnt);
        return rentCred;
    }

    /**
     * 公司业务员还车业务
     */
    public RepayCred repayCar(String companySalesmanName, String idNumber, String carNumber) {

        if (StrUtil.isBlank(companySalesmanName) || StrUtil.isBlank(idNumber) || StrUtil.isBlank(carNumber) ) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.BIND_ARGS_ERROR);
        }

        RentalInfo rentalInfo = rentalInfoRepository.findByIdNumberAndCarNumber(idNumber, carNumber);
        CompanySalesman companySalesman = companySalesmanRepository.findByCompanyName(companySalesmanName);
        Company company = companyRepository.findByCompanyName(companySalesman.getCompanyName());
        Car car = carRepository.findByCarNumber(carNumber);
        if (ObjectUtil.isNull(companySalesman)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.COMPANYS_EXIST);
        }
        if (ObjectUtil.isNull(rentalInfo)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.RENTINFO_EMPTY);
        }
        if (ObjectUtil.isNull(car)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.CAR_EXIST);
        }

        RepayCred repayCred = new RepayCred();
        repayCred.setCompanyName(company.getCompanyName());
        repayCred.setCredNumber(334);
        repayCred.setBrand(car.getBrand());
        repayCred.setDailyRent(car.getDailyRent());
        repayCred.setDeposit(rentalInfo.getDeposit());
        repayCred.setRentDeadLine(rentalInfo.getRentDeadLine());
        Integer theRent = 0;
        repayCred.setTheRent(theRent);
        Integer payAmount = 0;
        repayCred.setPayAmount(payAmount);
        Integer change = 0;
        repayCred.setChange(change);
        Integer depositRefund = 0;
        repayCred.setDepositRefund(depositRefund);

//        RentCred rentCred = rentCredRepository.save(repayCred);

        rentalInfo.setRepayAgentNumber(String.valueOf(companySalesman.getAid()));
        RentalInfo rentalInfoBase = rentalInfoRepository.save(rentalInfo);

        return repayCred;
    }

    /**
     * 管理员可维护车辆的基本信息
     */
    public Boolean adminUpdateCar(Long aid, Car car) {

        Optional<Car> byId = carRepository.findById(car.getId());
        Optional<CompanyAdmin> byId1 = companyAdminRepository.findById(aid);
        if (ObjectUtil.isNull(byId)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.CAR_EXIST);
        }
        if (ObjectUtil.isNull(byId1)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.ADMIN_EMPTY);
        }

        carRepository.save(car);
        return Boolean.TRUE;
    }

    /**
     * 管理员可维护出租人的基本信息
     */
    public Boolean adminUpdateLeaser(Long aid, Leaser leaser) {

        Optional<Leaser> byId = leaserRepository.findById(leaser.getId());
        Optional<CompanyAdmin> byId1 = companyAdminRepository.findById(aid);
        if (ObjectUtil.isNull(byId)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.CAR_EXIST);
        }
        if (ObjectUtil.isNull(byId1)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.ADMIN_EMPTY);
        }

        leaserRepository.save(leaser);
        return Boolean.TRUE;
    }

    /**
     * 查看每辆车某个时间段的出租天数、盈利状况
     */
    public void adminGetDayAndprofit() {

    }

}
