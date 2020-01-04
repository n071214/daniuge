package com.kgc.service;

import com.kgc.dto.Exposer;
import com.kgc.dto.SeckillExecution;
import com.kgc.exception.RepeatKillException;
import com.kgc.exception.SeckillCloseException;
import com.kgc.exception.SeckillException;
import com.kgc.pojo.Seckill;

import java.util.List;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 08:52
 * @Deprecated: 业务接口:站在 使用者的角度去设计接口
 */
public interface SeckillService {


    /**
     * @deprecated 查询所有秒杀接口
     * @return
     */
    List<Seckill> getSeckillList();


    /**
     * @deprecated 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(Integer seckillId);


    /**
     * @deprecated 秒杀接口是输出秒杀接口地址
     *              否则输出系统时间和秒杀地址---达到的效果 没有到秒杀的时间,用户是得不到秒杀的地址-防止恶意篡改 url
     * @param seckillId
     */
    Exposer exportSeckillUrl(Integer seckillId);


    /**
     * @deprecated 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5 匹配md5是否一致，判断用户秒杀地址是否正常
     */
    SeckillExecution executeSeckill(Integer seckillId, String md5, String userPhone)throws SeckillException, RepeatKillException, SeckillCloseException;

}
