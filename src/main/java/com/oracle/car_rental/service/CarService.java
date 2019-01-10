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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private RepayCredRepository repayCredRepository;

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
    public List<LeaserCarVO> leaserGetCar(Long aid, String idNumber) {

        if (ObjectUtil.isNull(aid) || StrUtil.isBlank(idNumber)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.AIDORIDNUMBER_EMPTY_EORROR);
        }
        Leaser byAid = leaserRepository.findByAid(aid);
        if (ObjectUtil.isNull(byAid)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.WRONG_ACCOUNT);
        }
        List<Car> carList = carRepository.findAllByIdNumber(idNumber);

        List<LeaserCarVO> leaserCarVOS = carList.stream().map(CarVoUtil::toLeaserCarVo).collect(toList());

        return leaserCarVOS;
    }

    /**
     * 公司业务员租车业务
     * 只有正常状态的车辆可出租
     */
    public RentCred rentCar(Long salesmanAid, String idNumber, String carNumber, Integer rentDay, Integer deposit) {

        if (ObjectUtil.isNull(salesmanAid) || StrUtil.isBlank(idNumber) || StrUtil.isBlank(carNumber) ||
                ObjectUtil.isNull(rentDay) || ObjectUtil.isNull(deposit)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.ARGS_EMPTY_ERROR);
        }

        // 先判断对应的数据是否有
        Car car = carRepository.findByCarNumber(carNumber);
        Customers customers = customersRepository.findByIdNumber(idNumber);
        log.info("customers == {} ", customers);
        log.info("companySalesmanName == {} ", salesmanAid);
//        CompanySalesman companySalesman = companySalesmanRepository.findBySalesmanName(salesmanName);
        CompanySalesman companySalesman = companySalesmanRepository.findByAid(salesmanAid);
//        log.info("byAid == {} ", byAid);
        log.info("0===== {}", companySalesman);
        log.info("{}", companyRepository);
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

        // 改变汽车状态
        car.setIsRent("出租中");
        carRepository.save(car);

        // 可以出租汽车
        // 打印租车凭证
        RentCred rentCred = new RentCred();
        rentCred.setCarNumber(car.getCarNumber());
        rentCred.setBrand(car.getBrand());
        rentCred.setCompanyName(company.getCompanyName());
        rentCred.setPhoneNumber(company.getPhoneNumber());
        rentCred.setDailyRent(car.getDailyRent());
        // 押金
        rentCred.setDeposit(deposit);

        // 时间
        String rentDate = getCurrDate();
        String repayDate = getaddDate(rentDay);
//        Date rentDate = new Date();
//        Date repayDate = new Date();
        rentCred.setRentStartTime(rentDate);
//        rentCred.setPreDeadLine(date.);  ======
        rentCred.setRentAgentNumber(companySalesman.getAid().toString());

        RentCred save = rentCredRepository.save(rentCred);

        // 租车记录信息
        RentalInfo rentalInfo = new RentalInfo();
        rentalInfo.setCarNumber(car.getCarNumber());
        rentalInfo.setIdNumber(customers.getIdNumber());
        rentalInfo.setName(customers.getName());
        rentalInfo.setPhoneNumber(customers.getPhoneNumber());
        rentalInfo.setRentTime(rentDate);
        rentalInfo.setRentDeadLine(repayDate);
        rentalInfo.setRentAgentNumber(companySalesman.getAid().toString());
        rentalInfoRepository.save(rentalInfo);

        return rentCred;
    }

    /**
     * 公司业务员还车业务
     */
    public RepayCred repayCar(Long salesmanAid, String idNumber, String carNumber) {

        if (ObjectUtil.isNull(salesmanAid) || StrUtil.isBlank(idNumber) || StrUtil.isBlank(carNumber) ) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.BIND_ARGS_ERROR);
        }

        RentalInfo rentalInfo = rentalInfoRepository.findByIdNumberAndCarNumber(idNumber, carNumber);
        CompanySalesman companySalesman = companySalesmanRepository.findByAid(salesmanAid);
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

        // 获取租车时间
        String rentDate = rentalInfo.getRentTime();
        // 获取现在的换车时间
        String repayDate = getCurrDate();
        // 获取实际租车的时间
        Integer rent_day = Math.toIntExact(getDateSub(rentDate, repayDate));

        // 打印换车凭证
        RepayCred repayCred = new RepayCred();
        repayCred.setCompanyName(company.getCompanyName());
        repayCred.setCredNumber(334);
        repayCred.setBrand(car.getBrand());
        repayCred.setDailyRent(car.getDailyRent());
        repayCred.setDeposit(rentalInfo.getDeposit());
        repayCred.setRentDeadLine(rentalInfo.getRentDeadLine());

        // 租金
        Integer theRent = rent_day * repayCred.getDailyRent();
        repayCred.setTheRent(theRent);

        // 补交金额 = 押金 - 租金
        Integer payAmount = theRent - repayCred.getDeposit();
        repayCred.setPayAmount(payAmount);

        // 找零 = 押金 - 租金
        Integer change = theRent - repayCred.getDeposit();
        repayCred.setChange(change);

        // 押金退还（押金-租金）
        Integer depositRefund = theRent - repayCred.getDeposit();
        repayCred.setDepositRefund(depositRefund);

        RepayCred repayCredSave = repayCredRepository.save(repayCred);


        // 汽车盈利
        car.setProfit(theRent);
        car.setIsRent("空闲");
        carRepository.save(car);
        // 记录加换车时间
        rentalInfo.setRentDeadLine(getCurrDate());
        rentalInfo.setRepayAgentNumber(String.valueOf(companySalesman.getAid()));

        return repayCredSave;
    }

    /**
     * 管理员可维护车辆的基本信息
     */
    public Boolean adminUpdateCar(Long aid, Car car) {

        Optional<Car> byId = carRepository.findById(car.getId());
        CompanyAdmin byId1 = companyAdminRepository.findByAid(aid);
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
        CompanyAdmin byId1 = companyAdminRepository.findByAid(aid);
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

    // 得现在的日期
    public String getCurrDate() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        return currdate;
    }

    // 得增加天数后的日期
    public String getaddDate(int num) {

        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);

        d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    // 求日期差
    public Long getDateSub(String dateStr, String dateStr2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long day = 0L;
        try
        {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);
            day = (date2.getTime() - date.getTime())/1000/60/60/24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

}
