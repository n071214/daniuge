package com.kgc.web;

import com.alibaba.fastjson.JSON;
import com.kgc.dto.Exposer;
import com.kgc.dto.SeckillExecution;
import com.kgc.dto.SeckillResult;
import com.kgc.enums.SeckillStateEnum;
import com.kgc.exception.RepeatKillException;
import com.kgc.exception.SeckillCloseException;
import com.kgc.pojo.Seckill;
import com.kgc.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 15:26
 * @Deprecated: Seckill WEB层
 */
@Controller
@RequestMapping("/seckill")  // url:/模块/资源/{id}/细分      ----/seckill/list
public class SeckillController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * @deprecated 秒杀商品的列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getList(Model model){
        //获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list",seckillList);
        //list.jsp + model = ModelAndView
        return "list"; //WEB-INF/jsp/"list".jsp
    }

    /**
     * @deprecated 获取秒杀对象的详情
     * @param model
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("seckillId") Integer seckillId){
        if(seckillId == null){
            //如果传过来的时候id为空 那么就重定向到list
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forword:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }


    /**
     * produces作用就是告诉我们的浏览器我们的传输类型 是json 如果里面含有中文的意思的话就charset=UTF-8
     * @deprecated 查询接口暴露地址  ajax json 传递方式
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Integer seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }


    /**
     * @deprecated 执行秒杀操作
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Integer seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) String userPhone){
                                                    //如果传递过来的请求没有这个手机号,那么springmvc就会报错，那么我们就
                                                    //设置CookieValue中的一个属性required = false  就告诉springmvc 这个参数不是必须的

        if(userPhone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, md5, userPhone);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (RepeatKillException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (SeckillCloseException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);

        }
    }

//    /**
//     * @deprecated 获取当前系统时间
//     * @return
//     */
//    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
//    @ResponseBody
//    public JSON time(){
//        Date now = new Date();
//
//        System.out.println(now.getTime()+"====");
//        System.out.println(new SeckillResult(true,now.getTime())+"----------系统当前时间--------------");
//        SeckillResult<Long> sr=new SeckillResult(true,now.getTime());
//        return (JSON) JSON.toJSON(sr);
//    }

    /**
     * @deprecated 获取当前系统时间
     * @return
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();

        System.out.println(now.getTime()+"====");
        System.out.println(new SeckillResult(true,now.getTime())+"----------系统当前时间--------------");
        return new SeckillResult(true,now.getTime());
    }

}
