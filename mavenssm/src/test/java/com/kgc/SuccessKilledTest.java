package com.kgc;

import com.kgc.dao.SuccessKilledMapper;
import com.kgc.pojo.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-24 17:03
 * @Deprecated: 测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledTest {

    @Resource
    private SuccessKilledMapper successKilledMapper;


    @Test
    public void testInsertSuccessKilled(){
        //为了防止有用户重复进行秒杀操作 我们在建表的时候 id userPhone 配置成联合主键
                                //alter table TABNAME add primary key(another_col,...);
        int count = successKilledMapper.insertSuccessKilled(2,"12345678901");
        System.out.println("count="+count);

        /**
         * Preparing: insert into success_killed ( seckill_id, user_phone ) values ( ?, ? )
         * Parameters: 1(Integer), 18701314341(String)
         * Updates: 1
         *
         * count=1
         */
    }

    @Test
    public void testSlectBySeckillIdWithSeckill(){
        SuccessKilled successKilled = successKilledMapper.selectBySeckillIdWithSeckill(1,"18701314341");
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }
}
