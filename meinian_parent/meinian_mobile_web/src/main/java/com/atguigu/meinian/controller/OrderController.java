package com.atguigu.meinian.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constant.MessageConstant;
import com.atguigu.meinian.constant.RedisMessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.service.OrderService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map map){
        //获取手机号
        String telephone = (String) map.get("telephone");
        //获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        //获取redis中的正确的验证码
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(telephone + ":" + RedisMessageConstant.SENDTYPE_ORDER);
        //验证验证码是否过期（redis中是否存在正确的验证码）或用户输入的验证码是否正确
        if (codeInRedis == null || !validateCode.equals(codeInRedis)){
            //表示验证码验证失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        try {
            return orderService.saveOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping("/findOrderById")
    public Result findOrderById(Integer id){
        Map map = orderService.findOrderById(id);
        Date orderDate = (Date) map.get("orderDate");
        try {
            String date = DateUtils.parseDate2String(orderDate);
            map.put("orderDate", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
    }

}
