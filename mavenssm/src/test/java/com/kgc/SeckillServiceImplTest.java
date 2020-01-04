package com.kgc;

import com.kgc.dto.Exposer;
import com.kgc.dto.SeckillExecution;
import com.kgc.pojo.Seckill;
import com.kgc.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 10:39
 * @Deprecated:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class SeckillServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeckillServiceImplTest.class);
    @Autowired
    private SeckillService seckillService;


    @Test
    public void testGetSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        LOGGER.info("list={}",list);
    }

    @Test
    public void testGetById() {
        Integer id = 1;
        Seckill seckill = seckillService.getById(id);
        LOGGER.info("seckill = {}",seckill);
    }

    @Test
    public void testExportSeckillUrl() {
        Integer id = 1;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        LOGGER.info("result={}",exposer);
        System.out.println("Result="+exposer);
        /**
         * Exposer{exposed=true,
         *          md5='84ca0f1a4e2e9e54699fbac090db8231',
         *          seckillId=1,
         *          now=0,
         *          start=0,
         *          end=0}
         */
    }

    @Test
    public void testExecuteSeckill() {
        Integer id = 1;
        String userPhone = "1870993841";
        String md5 = "84ca0f1a4e2e9e54699fbac090db8231";
        SeckillExecution execution = seckillService.executeSeckill(id,userPhone,md5);
        LOGGER.info("result={}",execution);
    }



}
