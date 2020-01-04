package com.kgc.dao;

import com.kgc.pojo.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledMapper {

    /**
     * @deprecated 插入成功购买明细 可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") Integer seckillId, @Param("userPhone") String userPhone);


    /**
     * @deprecated 根据id查询SuccessKilled 并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled selectBySeckillIdWithSeckill(@Param("seckillId") Integer seckillId,@Param("userPhone") String userPhone);


    int insert(SuccessKilled record);

    int insertSelective(SuccessKilled record);

}