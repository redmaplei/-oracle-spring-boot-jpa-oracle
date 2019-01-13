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
import java.util.*;

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
//        if (!customers.getRentCar().equals("无")) {
//            throw new FrameRuntimeException(FrameErrorCodeEnums.)
//        }

        CompanySalesman companySalesman = companySalesmanRepository.findByAid(salesmanAid);
        List<Company> company = companyRepository.findAllByCompanyName(companySalesman.getCompanyName());
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

        // 由于jpa orm不支持自增主键
        List<RentCred> all = rentCredRepository.findAll();
        log.info("===== {}", all);
        if (all.isEmpty()) {
            rentCred.setId(0L);
        } else {
            rentCred.setId(all.get(all.size() - 1).getId() + 1);
        }

        // 号数
        Random ra = new Random();
        int i = ra.nextInt(1000) + 300;
        rentCred.setCredNumber(456 + i);

        rentCred.setCarNumber(car.getCarNumber());
        rentCred.setBrand(car.getBrand());
        rentCred.setCompanyName(company.get(0).getCompanyName());
        rentCred.setPhoneNumber(company.get(0).getPhoneNumber());
        rentCred.setDailyRent(car.getDailyRent());
        // 押金
        rentCred.setDeposit(deposit);

        // 时间
        String rentDate = getCurrDate();
        String repayDate = getaddDate(rentDay);
        rentCred.setRentStartTime(rentDate);
        rentCred.setRentAgentNumber(companySalesman.getAid().toString());

        RentCred save = rentCredRepository.save(rentCred);

        // 租车记录信息
        RentalInfo rentalInfo = new RentalInfo();
        // 由于jpa orm不支持自增主键
        List<RentalInfo> all1 = rentalInfoRepository.findAll();
        rentalInfo.setId(all1.get(all1.size() - 1).getId() + 1);

        rentalInfo.setCarNumber(car.getCarNumber());
        rentalInfo.setIdNumber(customers.getIdNumber());
        rentalInfo.setName(customers.getName());
        rentalInfo.setPhoneNumber(customers.getPhoneNumber());
        rentalInfo.setRentTime(rentDate);
        rentalInfo.setRentDeadLine(repayDate);
        rentalInfo.setRentAgentNumber(companySalesman.getAid().toString());
        rentalInfo.setDeposit(rentCred.getDeposit());
        rentalInfoRepository.save(rentalInfo);

        // 租车成功 rentCar会显示 租车的车牌号
        customers.setRentCar("租车" + car.getCarNumber());
        customersRepository.save(customers);

        return rentCred;
    }

    /**
     * 公司业务员还车业务
     */
    public RepayCred repayCar(Long salesmanAid, String idNumber, String carNumber) {

        if (ObjectUtil.isNull(salesmanAid) || StrUtil.isBlank(idNumber) || StrUtil.isBlank(carNumber) ) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.BIND_ARGS_ERROR);
        }

        Customers customers = customersRepository.findByIdNumber(idNumber);
        if (customers.getRentCar().equals("无")) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.RENT_EMPTY_EORROR);
        }

        RentalInfo rentalInfo = rentalInfoRepository.findByIdNumberAndCarNumberAndRepayAgentNumber(idNumber, carNumber, null);
        CompanySalesman companySalesman = companySalesmanRepository.findByAid(salesmanAid);
        List<Company> company = companyRepository.findAllByCompanyName(companySalesman.getCompanyName());
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

        // 由于jpa orm不支持自增主键
        List<RepayCred> all = repayCredRepository.findAll();
        if (ObjectUtil.isNull(all)) {
            repayCred.setId(0L);
        } else {
            repayCred.setId(all.get(all.size() - 1).getId() + 1);
        }

        repayCred.setCompanyName(company.get(0).getCompanyName());
        repayCred.setCarNumber(car.getCarNumber());
        // 号数
        Random ra = new Random();
        int i = ra.nextInt(1000) + 300;
        repayCred.setCredNumber(334 + i);
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
        Integer depositRefund = repayCred.getDeposit() - theRent;
        repayCred.setDepositRefund(depositRefund);

        RepayCred repayCredSave = repayCredRepository.save(repayCred);


        // 汽车盈利
        car.setProfit(theRent);
        car.setIsRent("空闲");
        carRepository.save(car);
        // 记录加换车时间
        rentalInfo.setRentDeadLine(getCurrDate());
        rentalInfo.setRepayAgentNumber(String.valueOf(companySalesman.getAid()));
        rentalInfoRepository.save(rentalInfo);

        // 出租人租车状态修改
        customers.setRentCar("无");
        customersRepository.save(customers);

        return repayCredSave;
    }

    /**
     * 管理员可维护车辆的基本信息
     */
    public Boolean adminUpdateCar(Long aid, Car car) {

        if (ObjectUtil.isNull(aid) || ObjectUtil.isNull(car)) {
            throw new FrameRuntimeException(FrameErrorCodeEnums.ARGS_EMPTY_ERROR);
        }

        Optional<Car> byId = carRepository.findById(car.getId());
        CompanyAdmin byId1 = companyAdminRepository.findByAid(aid);
        if (!byId.isPresent()) {
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
        if (!byId.isPresent()) {
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
