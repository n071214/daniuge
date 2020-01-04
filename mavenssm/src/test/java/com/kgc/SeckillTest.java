package com.kgc;

import com.kgc.dao.SeckillMapper;
import com.kgc.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-24 15:41
 * @Deprecated:  5.1 配置spring和Junit整合,Junit启动式加载SpringIOC容器
 *              Spring-test
 */


@RunWith(SpringJUnit4ClassRunner.class)
//5.2 --告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillTest {


    //5.3 注入Dao实现类
    @Resource
    private SeckillMapper seckillMapper;

    @Test
    public void testSelectByPrimaryKey(){
        Seckill seckill = seckillMapper.selectByPrimaryKey(1);
        System.out.println("商品的名称："+seckill.getName());
        //打印所有
        System.out.println(seckill);
        /**
         * 商品的名称：1元秒杀坚果tNT工作站
         * Seckill{seckillId=1, name='1元秒杀坚果tNT工作站',
         * number=100,
         * startTime=Fri Jun 01 00:00:00 CST 2018,
         * endTime=Sat Jun 02 00:00:00 CST 2018,
         * createTime=Wed Jul 24 14:33:56 CST 2019}
         *
         */
    }

    @Test
    public void selectSeckillList(){
        List<Seckill> seckillList = seckillMapper.selectSeckillList(0,3);
        if(seckillList != null){
            for (Seckill  seckill : seckillList) {
                System.out.println("商品的名称为："+seckill.getName());
            }
        }
        /**
         * 商品的名称为：1元秒杀坚果tNT工作站
         * 商品的名称为：1元秒杀iphonex
         * 商品的名称为：1元秒杀坚果3
         */
    }

    @Test
    public void reduceNumber(){
        int count = seckillMapper.reduceNumber(1,new Date());
        System.out.println("count = "+ count);
    }
    /**
     *   update seckill set number = number - 1
     *   where seckill_id = ?
     *   and start_time <= ?
     *   and end_time >= ?
     *   and number > 0
     *  Parameters: 1(Integer), 2019-07-24 17:00:05.698(Timestamp), 2019-07-24 17:00:05.698(Timestamp)
     */
}
