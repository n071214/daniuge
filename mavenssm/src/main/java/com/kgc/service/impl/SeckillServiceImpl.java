package com.kgc.service.impl;

import com.kgc.dao.SeckillMapper;
import com.kgc.dao.SuccessKilledMapper;
import com.kgc.dto.Exposer;
import com.kgc.dto.SeckillExecution;
import com.kgc.enums.SeckillStateEnum;
import com.kgc.exception.RepeatKillException;
import com.kgc.exception.SeckillCloseException;
import com.kgc.exception.SeckillException;
import com.kgc.pojo.Seckill;
import com.kgc.pojo.SuccessKilled;
import com.kgc.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 09:48
 * @Deprecated: 秒杀接口实现类
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //自动注入  注入Service 依赖
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SuccessKilledMapper successKilledMapper;

    private final String salt = "23456$%^&skajsfsjb%^&*(";


    /**
     * @deprecated md5加密
     * @param seckillId
     * @return
     */
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        System.out.println("加密后的md5" + md5);
        return md5;
    }


    /**
     * @return
     * @deprecated 查询所有秒杀接口
     */
    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.selectSeckillList(0,4);
    }

    /**
     * @param seckillId
     * @return
     * @deprecated 查询单个秒杀记录
     */
    @Override
    public Seckill getById(Integer seckillId) {
        return seckillMapper.selectByPrimaryKey(seckillId);
    }





    /**
     * @param seckillId
     * @deprecated 秒杀接口是输出秒杀接口地址
     * 否则输出系统时间和秒杀地址---达到的效果 没有到秒杀的时间,用户是得不到秒杀的地址-防止恶意篡改 url
     */
    @Override
    public Exposer exportSeckillUrl(Integer seckillId) {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        if(seckill == null){
            return new Exposer(false,seckillId);

        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();

        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){

            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        //md5加密
        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }



    /**
     * @param seckillId
     * @param userPhone
     * @param md5       匹配md5是否一致，判断用户秒杀地址是否正常
     * @deprecated 执行秒杀操作
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Integer seckillId, String md5,String userPhone)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        //如果md5的值不匹配，则判断秒杀地址错误，返回秒杀数据被重写
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
           //减库存
           int updateCount = seckillMapper.reduceNumber(seckillId,nowTime);
           if(updateCount <= 0){
               //没有更新记录,秒杀结束
               throw new SeckillCloseException("seckill is closed");
           }else{
               //如果有减库存的话就对应的有购买记录
               int insertCount = successKilledMapper.insertSuccessKilled(seckillId,userPhone);
               //唯一的：联合主键 seckillId userPhone
               if(insertCount <= 0){
                   //重复秒杀的行为
                   throw new RepeatKillException("seckill repeated");
               }else {
                   //秒杀成功
                   SuccessKilled successKilled = successKilledMapper.selectBySeckillIdWithSeckill(seckillId, userPhone);
                   return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
               }

           }
       }catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        }catch (Exception e){
           logger.error(e.getMessage(),e);
            //所有编译器异常转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
       }

    }
}
