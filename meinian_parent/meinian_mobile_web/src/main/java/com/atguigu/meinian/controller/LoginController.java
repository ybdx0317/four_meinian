package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constant.MessageConstant;
import com.atguigu.meinian.constant.RedisMessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.Member;
import com.atguigu.meinian.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/checkLogin")
    public Result checkLogin(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        //获取redis中存储的正确的验证码
        String codeInRedis = jedis.get(telephone + ":" + RedisMessageConstant.SENDTYPE_LOGIN);
        //判断验证码是否正确
        if(codeInRedis == null || !validateCode.equals(codeInRedis)){
            //验证码错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //判断当前手机号是否注册了会员，若没有注册则自动注册
            Member member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.addMember(member);
            //验证码正确
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }


}
