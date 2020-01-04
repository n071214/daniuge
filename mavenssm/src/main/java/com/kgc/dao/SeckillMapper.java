package com.kgc.dao;

import com.kgc.pojo.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillMapper {


    /**
     * @deprecated 根据商品编号去查询商品秒杀库存表
     * @param seckillId
     * @return
     */
    Seckill selectByPrimaryKey(Integer seckillId);

    /**
     * @deprecated 减库存的操作
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") Integer seckillId, @Param("killTime") Date killTime);

    /**
     * @deprecated 给前台展示的一个商品列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Seckill> selectSeckillList(@Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);

    int deleteByPrimaryKey(Integer seckillId);

    int insert(Seckill record);

    int insertSelective(Seckill record);



    int updateByPrimaryKeySelective(Seckill record);

    int updateByPrimaryKey(Seckill record);
}