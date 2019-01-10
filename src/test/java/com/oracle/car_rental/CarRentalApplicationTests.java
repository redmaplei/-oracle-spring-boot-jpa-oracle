package com.oracle.car_rental;

import com.oracle.car_rental.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRentalApplicationTests {

	@Autowired
	private AccountRepository accountRepository;

//	@Autowired
//	private TRepository tRepository;

	@Test
	public void contextLoads() {

//		log.info("sdf {}", accountDao.createLambdaQuery().select());
//		log.info("sdf {}", tRepository.findAll());

		log.info("24 {}", accountRepository.findAll());

	}

}

